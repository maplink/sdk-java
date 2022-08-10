package global.maplink.credentials;

import global.maplink.token.MapLinkToken;
import global.maplink.token.TokenProvider;

import java.util.concurrent.CompletableFuture;

public interface MapLinkCredentials {
    CompletableFuture<MapLinkToken> fetchToken(TokenProvider provider);

    static MapLinkCredentials loadDefault() {
        return new EnvMapLinkCredentials();
    }

    static MapLinkCredentials ofKey(String clientId, String password) {
        return new ProvidedMapLinkCredentials(clientId, password);
    }
}
