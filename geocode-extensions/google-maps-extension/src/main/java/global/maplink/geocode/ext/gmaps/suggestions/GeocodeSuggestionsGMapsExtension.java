package global.maplink.geocode.ext.gmaps.suggestions;

import global.maplink.MapLinkSDK;
import global.maplink.geocode.ext.gmaps.GeocodeGMapsConfig;
import global.maplink.geocode.extensions.GeocodeExtension;
import global.maplink.geocode.schema.suggestions.SuggestionsRequest;
import global.maplink.geocode.schema.suggestions.SuggestionsResult;
import global.maplink.http.HttpAsyncEngine;
import global.maplink.http.Response;
import global.maplink.json.JsonMapper;
import lombok.RequiredArgsConstructor;

import java.net.URL;
import java.util.concurrent.CompletableFuture;
import java.util.function.Function;

import static global.maplink.helpers.UrlHelper.urlFrom;
import static global.maplink.http.request.Request.get;
import static java.util.Optional.ofNullable;
import static java.util.concurrent.CompletableFuture.completedFuture;

@RequiredArgsConstructor
public class GeocodeSuggestionsGMapsExtension implements GeocodeExtension<SuggestionsRequest> {

    private static final Class<SuggestionsRequest> REQUEST_TYPE = SuggestionsRequest.class;

    private static final String GMAPS_URL = "https://maps.googleapis.com/maps/api/geocode/json";
    private static final String PARAM_QUERY = "address";
    private static final String PARAM_KEY = "key";
    private static final String PARAM_LANG = "language";
    private static final String PARAM_LANG_DEFAULT = "pt-BR";
    private static final double THRESHOLD = 70.0;

    private final GeocodeGMapsConfig config;
    private final URL url = urlFrom(GMAPS_URL);
    private HttpAsyncEngine http;
    private JsonMapper mapper;
    private boolean initialized = false;

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
        return action.apply(request).thenCompose(afterRequest(request));
    }

    private Function<SuggestionsResult, CompletableFuture<SuggestionsResult>> afterRequest(SuggestionsRequest request) {
        return result -> {
            if (!initialized || hasGoodScore(result)) {
                return completedFuture(result);
            }
            return delegateToGmaps(request);
        };
    }

    private CompletableFuture<SuggestionsResult> delegateToGmaps(SuggestionsRequest request) {
        return http.run(get(url)
                .withQuery(PARAM_KEY, config.getApiKey())
                .withQuery(PARAM_LANG, PARAM_LANG_DEFAULT)
                .withQuery(PARAM_QUERY, request.getQuery())
        ).thenApply(this::parse);
    }

    private SuggestionsResult parse(Response response) {
        response.throwIfIsError();
        GeocodeGMapsResponse body = response.parseBodyObject(mapper, GeocodeGMapsResponse.class);
        if (body.isDenied()) {
            throw new IllegalArgumentException(body.getStatus() + ": " + body.getError_message());
        }
        return body.toSuggestions();
    }

    private boolean hasGoodScore(SuggestionsResult result) {
        return ofNullable(result.getMostRelevant())
                .map(r -> r.getScore() > THRESHOLD)
                .orElse(false);
    }

    @Override
    public String getName() {
        return "Geocode Suggestions - Google Maps Extension";
    }

    @Override
    public Class<SuggestionsRequest> getRequestType() {
        return REQUEST_TYPE;
    }
}
