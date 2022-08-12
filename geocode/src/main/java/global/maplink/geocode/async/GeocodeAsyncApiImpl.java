package global.maplink.geocode.async;

import global.maplink.credentials.MapLinkCredentials;
import global.maplink.env.Environment;
import global.maplink.geocode.common.Type;
import global.maplink.geocode.geocode.GeocodeRequest;
import global.maplink.geocode.suggestions.SuggestionsRequest;
import global.maplink.geocode.suggestions.SuggestionsResponse;
import global.maplink.http.HttpAsyncEngine;
import global.maplink.http.Response;
import global.maplink.http.request.Request;
import global.maplink.http.request.RequestBody;
import global.maplink.json.JsonMapper;
import global.maplink.token.TokenProvider;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.val;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;

import static global.maplink.http.request.Request.get;
import static global.maplink.http.request.Request.post;
import static java.lang.String.format;

@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
public class GeocodeAsyncApiImpl implements GeocodeAsyncAPI {

    private static final String SUGGESTIONS_PATH = "geocode/v1/suggestions";

    private static final String GEOCODE_PATH = "geocode/v1/geocode";

    private static final String MULTI_GEOCODE_PATH = "geocode/v1/multi-geocode";

    private final Environment environment;

    private final HttpAsyncEngine http;

    private final JsonMapper mapper;

    private final TokenProvider tokenProvider;

    private final MapLinkCredentials credentials;

    @Override
    public CompletableFuture<SuggestionsResponse> suggestions(SuggestionsRequest request) {
        val httpRequest = get(environment.withService(SUGGESTIONS_PATH))
                .withQuery("q", request.getQuery());
        Optional.ofNullable(request.getType())
                .map(Type::name)
                .ifPresent(v -> httpRequest.withQuery("type", v));

        return doRequest(httpRequest).thenApply(this::parse);
    }

    @Override
    public CompletableFuture<SuggestionsResponse> geocode(GeocodeRequest request) {
        if (request instanceof GeocodeRequest.Single) return singleGeocode((GeocodeRequest.Single) request);
        if (request instanceof GeocodeRequest.Multi) return multiGeocode((GeocodeRequest.Multi) request);

        throw new IllegalArgumentException(format("Invalid GeocodeRequest of [type: %s]: %s", request.getClass(), request));
    }

    private CompletableFuture<SuggestionsResponse> singleGeocode(GeocodeRequest.Single request) {
        return doRequest(post(
                environment.withService(GEOCODE_PATH),
                RequestBody.Json.of(request, mapper)
        )).thenApply(this::parse);
    }

    private CompletableFuture<SuggestionsResponse> multiGeocode(GeocodeRequest.Multi request) {
        return doRequest(post(
                environment.withService(MULTI_GEOCODE_PATH),
                RequestBody.Json.of(request.getRequests(), mapper)
        )).thenApply(this::parse);
    }


    private CompletableFuture<Response> doRequest(Request httpRequest) {
        return credentials.fetchToken(tokenProvider)
                .thenCompose(token -> http.run(httpRequest.withAuthorizationHeader(token.asHeaderValue())));
    }

    private SuggestionsResponse parse(Response response) {
        return response.parseBodyObject(mapper, SuggestionsResponse.class);
    }
}
