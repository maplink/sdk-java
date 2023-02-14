package global.maplink.freight.async;

import global.maplink.credentials.MapLinkCredentials;
import global.maplink.env.Environment;
import global.maplink.freight.schema.FreightCalculationRequest;
import global.maplink.freight.schema.FreightCalculationResponse;
import global.maplink.http.HttpAsyncEngine;
import global.maplink.json.JsonMapper;
import global.maplink.token.TokenProvider;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.val;

import java.util.concurrent.CompletableFuture;

@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class FreightAsyncApiImpl implements FreightAsyncAPI {
    private final Environment environment;

    private final HttpAsyncEngine http;

    private final JsonMapper mapper;

    private final TokenProvider tokenProvider;

    private final MapLinkCredentials credentials;

    @Override
    public CompletableFuture<FreightCalculationResponse> calculate(FreightCalculationRequest request) {
        val httpRequest = request.asHttpRequest(environment, mapper);
        return credentials.fetchToken(tokenProvider)
                .thenCompose(token -> http.run(token.applyOn(httpRequest)))
                .thenApply(request.getResponseParser(mapper));
    }
}
