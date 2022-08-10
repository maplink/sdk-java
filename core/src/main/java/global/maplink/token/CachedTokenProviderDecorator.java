package global.maplink.token;

import lombok.RequiredArgsConstructor;
import lombok.val;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

import static java.util.Collections.synchronizedMap;
import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;
import static java.util.concurrent.CompletableFuture.completedFuture;

@RequiredArgsConstructor
public class CachedTokenProviderDecorator implements TokenProvider {

    private final TokenProvider delegate;

    private final Map<String, MapLinkToken> cache = synchronizedMap(new HashMap<>());

    @Override
    public CompletableFuture<MapLinkToken> getToken(String clientId, String secret) {
        val key = clientId + ":" + secret;

        if (cache.containsKey(key)) {
            MapLinkToken token = cache.get(key);
            if (!token.isAboutToExpireIn(30)) {
                return completedFuture(token);
            } else {
                cache.remove(key);
            }
        }

        return delegate.getToken(clientId, secret).whenComplete((t, e) -> {
            if (nonNull(t) && isNull(e)) {
                cache.put(key, t);
            }
        });
    }
}
