package global.maplink;

import global.maplink.credentials.MapLinkCredentials;
import global.maplink.env.Environment;
import global.maplink.http.HttpAsyncEngine;
import global.maplink.json.JsonMapper;
import global.maplink.token.TokenProvider;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.val;

import java.util.concurrent.CompletableFuture;

@RequiredArgsConstructor
public class MapLinkServiceRequestAsyncRunnerImpl implements MapLinkServiceRequestAsyncRunner {

    @Getter
    private final Environment environment;

    private final HttpAsyncEngine http;

    private final JsonMapper mapper;

    private final TokenProvider tokenProvider;

    private final MapLinkCredentials credentials;

    @Override
    public <T> CompletableFuture<T> run(MapLinkServiceRequest<T> request) {
        request.throwIfInvalid();
        val httpRequest = request.asHttpRequest(environment, mapper);
        return credentials.fetchToken(tokenProvider)
                .thenCompose(token -> http.run(token.applyOn(httpRequest)))
                .thenApply(request.getResponseParser(mapper));
    }

}
