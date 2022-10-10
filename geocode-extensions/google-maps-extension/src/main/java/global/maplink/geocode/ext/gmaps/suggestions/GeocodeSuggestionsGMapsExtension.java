package global.maplink.geocode.ext.gmaps.suggestions;

import global.maplink.MapLinkSDK;
import global.maplink.geocode.ext.gmaps.config.GeocodeGMapsConfig;
import global.maplink.geocode.extensions.GeocodeExtension;
import global.maplink.geocode.schema.suggestions.SuggestionsRequest;
import global.maplink.geocode.schema.suggestions.SuggestionsResult;
import global.maplink.http.HttpAsyncEngine;
import global.maplink.json.JsonMapper;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.val;

import java.net.URL;
import java.util.concurrent.CompletableFuture;
import java.util.function.Function;

import static global.maplink.helpers.UrlHelper.urlFrom;
import static global.maplink.http.request.Request.get;
import static java.lang.String.format;

@RequiredArgsConstructor
@EqualsAndHashCode
public class GeocodeSuggestionsGMapsExtension implements GeocodeExtension<SuggestionsRequest> {

    private static final Class<SuggestionsRequest> REQUEST_TYPE = SuggestionsRequest.class;

    private static final String GMAPS_URL = "https://maps.googleapis.com/maps/api/geocode/json";
    private static final String PARAM_QUERY = "address";
    private static final String PARAM_KEY = "key";
    private static final String PARAM_LANG = "language";
    private static final String PARAM_LANG_DEFAULT = "pt-BR";

    @Getter
    private boolean initialized = false;
    @Getter
    private final GeocodeGMapsConfig config;

    private final URL url = urlFrom(GMAPS_URL);
    private HttpAsyncEngine http;
    private JsonMapper mapper;


    public GeocodeSuggestionsGMapsExtension() {
        this(GeocodeGMapsConfig.fromEnv());
    }

    @Override
    public void initialize(MapLinkSDK sdk) {
        http = sdk.getHttp();
        mapper = sdk.getJsonMapper();
        initialized = true;
    }

    @Override
    public CompletableFuture<SuggestionsResult> doRequest(SuggestionsRequest request, Function<SuggestionsRequest, CompletableFuture<SuggestionsResult>> action) {
        if (!initialized) {
            throw new IllegalStateException(format("Extension: %s não inicializada corretamente", getName()));
        }
        return config.getSwitchStrategy().choose(request, new GMapsRequestActionImpl(), action::apply);
    }

    @Override
    public String getName() {
        return "Geocode Suggestions - Google Maps Extension";
    }

    @Override
    public Class<SuggestionsRequest> getRequestType() {
        return REQUEST_TYPE;
    }

    class GMapsRequestActionImpl implements GMapsSuggestionsRequestAction {

        @Override
        public CompletableFuture<GeocodeGMapsResponse> apply(SuggestionsRequest request) {
            return http.run(get(url)
                    .withQuery(PARAM_KEY, config.getApiKey())
                    .withQuery(PARAM_LANG, PARAM_LANG_DEFAULT)
                    .withQuery(PARAM_QUERY, request.getQuery())
            ).thenApply(response -> {
                response.throwIfIsError();
                val body = response.parseBodyObject(mapper, GeocodeGMapsResponse.class);
                if (body.isDenied()) {
                    throw new IllegalArgumentException(body.getStatus() + ": " + body.getError_message());
                }
                return body;
            });
        }
    }

}
