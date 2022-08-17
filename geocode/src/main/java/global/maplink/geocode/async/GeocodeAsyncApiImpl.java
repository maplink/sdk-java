package global.maplink.geocode.async;

import global.maplink.credentials.MapLinkCredentials;
import global.maplink.env.Environment;
import global.maplink.geocode.geocode.GeocodeRequest;
import global.maplink.geocode.reverse.ReverseRequest;
import global.maplink.geocode.schema.Type;
import global.maplink.geocode.schema.suggestions.SuggestionsResult;
import global.maplink.geocode.suggestions.SuggestionsRequest;
import global.maplink.http.HttpAsyncEngine;
import global.maplink.http.Response;
import global.maplink.http.request.Request;
import global.maplink.http.request.RequestBody;
import global.maplink.json.JsonMapper;
import global.maplink.token.TokenProvider;
import lombok.RequiredArgsConstructor;
import lombok.val;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;

import static global.maplink.http.request.Request.get;
import static global.maplink.http.request.Request.post;
import static java.lang.String.format;
import static lombok.AccessLevel.PACKAGE;

@RequiredArgsConstructor(access = PACKAGE)
public class GeocodeAsyncApiImpl implements GeocodeAsyncAPI {

    private static final String SUGGESTIONS_PATH = "geocode/v1/suggestions";

    private static final String GEOCODE_PATH = "geocode/v1/geocode";

    private static final String MULTI_GEOCODE_PATH = "geocode/v1/multi-geocode";

    private static final String REVERSE_GEOCODE_PATH = "geocode/v1/reverse";

    private final Environment environment;

    private final HttpAsyncEngine http;

    private final JsonMapper mapper;

    private final TokenProvider tokenProvider;

    private final MapLinkCredentials credentials;

    @Override
    public CompletableFuture<SuggestionsResult> suggestions(SuggestionsRequest request) {
        val httpRequest = get(environment.withService(SUGGESTIONS_PATH))
                .withQuery("q", request.getQuery());
        Optional.ofNullable(request.getType())
                .map(Type::name)
                .ifPresent(v -> httpRequest.withQuery("type", v));

        return doRequest(httpRequest).thenApply(this::parse);
    }

    @Override
    public CompletableFuture<SuggestionsResult> geocode(GeocodeRequest request) {
        if (request instanceof GeocodeRequest.Single) return singleGeocode((GeocodeRequest.Single) request);
        if (request instanceof GeocodeRequest.Multi) return multiGeocode((GeocodeRequest.Multi) request);

        throw new IllegalArgumentException(format("Invalid GeocodeRequest of [type: %s]: %s", request.getClass(), request));
    }

    @Override
    public CompletableFuture<SuggestionsResult> reverse(ReverseRequest request) {
        return doRequest(post(
                environment.withService(REVERSE_GEOCODE_PATH),
                RequestBody.Json.of(request.getEntries(), mapper)
        )).thenApply(this::parse);
    }

    private CompletableFuture<SuggestionsResult> singleGeocode(GeocodeRequest.Single request) {
        return doRequest(post(
                environment.withService(GEOCODE_PATH),
                RequestBody.Json.of(request, mapper)
        )).thenApply(this::parse);
    }

    private CompletableFuture<SuggestionsResult> multiGeocode(GeocodeRequest.Multi request) {
        return doRequest(post(
                environment.withService(MULTI_GEOCODE_PATH),
                RequestBody.Json.of(request.getRequests(), mapper)
        )).thenApply(this::parse);
    }


    private CompletableFuture<Response> doRequest(Request httpRequest) {
        return credentials.fetchToken(tokenProvider)
                .thenCompose(token -> http.run(httpRequest.withAuthorizationHeader(token.asHeaderValue())));
    }

    private SuggestionsResult parse(Response response) {
        return response.parseBodyObject(mapper, SuggestionsResult.class);
    }
}
