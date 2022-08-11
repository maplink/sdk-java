package global.maplink.geocode.async;

import global.maplink.credentials.MapLinkCredentials;
import global.maplink.env.Environment;
import global.maplink.geocode.common.Type;
import global.maplink.geocode.suggestions.SuggestionsRequest;
import global.maplink.geocode.suggestions.SuggestionsResponse;
import global.maplink.http.HttpAsyncEngine;
import global.maplink.http.request.Request;
import global.maplink.json.JsonMapper;
import global.maplink.token.TokenProvider;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.val;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;

@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
public class GeocodeAsyncApiImpl implements GeocodeAsyncAPI {

    private static final String SUGGESTIONS_PATH = "geocode/v1/suggestions";

    private final Environment environment;

    private final HttpAsyncEngine http;

    private final JsonMapper mapper;

    private final TokenProvider tokenProvider;

    private final MapLinkCredentials credentials;

    @Override
    public CompletableFuture<SuggestionsResponse> suggestions(SuggestionsRequest request) {
        val getRequest = Request.get(environment.withService(SUGGESTIONS_PATH))
                .withQuery("q", request.getQuery());
        Optional.ofNullable(request.getType())
                .map(Type::name)
                .ifPresent(v -> getRequest.withQuery("type", v));

        return credentials.fetchToken(tokenProvider)
                .thenCompose(token -> http.run(getRequest.withAuthorizationHeader(token.asHeaderValue())))
                .thenApply(v -> v.parseBodyObject(mapper, SuggestionsResponse.class));
    }

}
