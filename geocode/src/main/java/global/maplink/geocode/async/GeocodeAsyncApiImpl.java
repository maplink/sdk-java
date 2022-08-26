package global.maplink.geocode.async;

import global.maplink.MapLinkServiceRequest;
import global.maplink.credentials.MapLinkCredentials;
import global.maplink.env.Environment;
import global.maplink.geocode.schema.geocode.GeocodeRequest;
import global.maplink.geocode.schema.reverse.ReverseRequest;
import global.maplink.geocode.schema.suggestions.SuggestionsRequest;
import global.maplink.geocode.schema.suggestions.SuggestionsResult;
import global.maplink.http.HttpAsyncEngine;
import global.maplink.http.Response;
import global.maplink.json.JsonMapper;
import global.maplink.token.TokenProvider;
import lombok.RequiredArgsConstructor;
import lombok.val;

import java.util.concurrent.CompletableFuture;

import static lombok.AccessLevel.PACKAGE;

@RequiredArgsConstructor(access = PACKAGE)
public class GeocodeAsyncApiImpl implements GeocodeAsyncAPI {

    private final Environment environment;

    private final HttpAsyncEngine http;

    private final JsonMapper mapper;

    private final TokenProvider tokenProvider;

    private final MapLinkCredentials credentials;

    @Override
    public CompletableFuture<SuggestionsResult> suggestions(SuggestionsRequest request) {
        return doRequest(request).thenApply(this::parse);
    }

    @Override
    public CompletableFuture<SuggestionsResult> geocode(GeocodeRequest request) {
        return doRequest(request).thenApply(this::parse);
    }

    @Override
    public CompletableFuture<SuggestionsResult> reverse(ReverseRequest request) {
        return doRequest(request).thenApply(this::parse);
    }


    private CompletableFuture<Response> doRequest(MapLinkServiceRequest request) {
        val httpRequest = request.asHttpRequest(environment, mapper);
        return credentials.fetchToken(tokenProvider)
                .thenCompose(token -> http.run(token.applyOn(httpRequest)));
    }

    private SuggestionsResult parse(Response response) {
        return response.parseBodyObject(mapper, SuggestionsResult.class);
    }
}
