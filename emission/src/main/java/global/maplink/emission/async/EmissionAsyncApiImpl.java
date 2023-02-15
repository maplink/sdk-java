package global.maplink.emission.async;

import global.maplink.credentials.MapLinkCredentials;
import global.maplink.emission.schema.EmissionRequest;
import global.maplink.emission.schema.EmissionResponse;
import global.maplink.env.Environment;
import global.maplink.http.HttpAsyncEngine;
import global.maplink.json.JsonMapper;
import global.maplink.token.TokenProvider;
import lombok.RequiredArgsConstructor;
import lombok.val;

import java.util.concurrent.CompletableFuture;

import static lombok.AccessLevel.PACKAGE;

@RequiredArgsConstructor(access = PACKAGE)
public class EmissionAsyncApiImpl implements EmissionAsyncAPI {

    private final Environment environment;

    private final HttpAsyncEngine http;

    private final JsonMapper mapper;

    private final TokenProvider tokenProvider;

    private final MapLinkCredentials credentials;

    @Override
    public CompletableFuture<EmissionResponse> calculate(EmissionRequest request) {
        val httpRequest = request.asHttpRequest(environment, mapper);
        return credentials.fetchToken(tokenProvider)
                .thenCompose(token -> http.run(token.applyOn(httpRequest)))
                .thenApply(request.getResponseParser(mapper));
    }
}
