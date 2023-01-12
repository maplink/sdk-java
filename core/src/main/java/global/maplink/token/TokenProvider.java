package global.maplink.token;

import global.maplink.env.Environment;
import global.maplink.http.HttpAsyncEngine;
import global.maplink.json.JsonMapper;
import lombok.val;

import java.util.concurrent.CompletableFuture;

@FunctionalInterface
public interface TokenProvider {

    CompletableFuture<MapLinkToken> getToken(String clientId, String secret);

    static TokenProvider create(HttpAsyncEngine http, Environment env, JsonMapper mapper, boolean withCache) {
        val oauth = new OAuthTokenProvider(http, env, mapper);
        return withCache ? new CachedTokenProviderDecorator(oauth) : oauth;
    }
}
