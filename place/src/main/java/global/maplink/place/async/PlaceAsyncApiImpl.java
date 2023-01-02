package global.maplink.place.async;

import global.maplink.credentials.MapLinkCredentials;
import global.maplink.env.Environment;
import global.maplink.http.HttpAsyncEngine;
import global.maplink.json.JsonMapper;
import global.maplink.place.schema.PlaceRouteRequest;
import global.maplink.place.schema.PlaceRouteResponse;
import global.maplink.token.TokenProvider;
import lombok.RequiredArgsConstructor;
import lombok.val;

import java.util.concurrent.CompletableFuture;

@RequiredArgsConstructor
public class PlaceAsyncApiImpl implements PlaceAsyncAPI {
    private final Environment environment;

    private final HttpAsyncEngine http;

    private final JsonMapper mapper;

    private final TokenProvider tokenProvider;

    private final MapLinkCredentials credentials;

    @Override
    public CompletableFuture<PlaceRouteResponse> calculate(PlaceRouteRequest request) {
        val httpRequest = request.asHttpRequest(environment, mapper);
        return credentials.fetchToken(tokenProvider)
                .thenCompose(token -> http.run(token.applyOn(httpRequest)))
                .thenApply(r -> r.parseBodyObject(mapper, PlaceRouteResponse.class));
    }
}
