package global.maplink.credentials;

import global.maplink.token.MapLinkToken;
import global.maplink.token.TokenProvider;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.concurrent.CompletableFuture;

@RequiredArgsConstructor
@Getter
@EqualsAndHashCode
public class ProvidedMapLinkCredentials implements MapLinkCredentials {
    private final String clientId;

    private final String secret;

    @Override
    public CompletableFuture<MapLinkToken> fetchToken(TokenProvider provider) {
        return provider.getToken(clientId, secret);
    }
}
