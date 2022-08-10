package global.maplink.credentials;

import global.maplink.token.MapLinkToken;
import global.maplink.token.TokenProvider;
import lombok.Getter;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;

@Getter
public class EnvMapLinkCredentials implements MapLinkCredentials {

    private static final String PROPERTY_CLIENT_ID = "maplink.clientId";

    private static final String PROPERTY_SECRET = "maplink.secret";

    private static final String ENV_CLIENT_ID = "MAPLINK_CLIENT_ID";

    private static final String ENV_SECRET = "MAPLINK_SECRET";

    private final String key = load(PROPERTY_CLIENT_ID, ENV_CLIENT_ID);

    private final String secret = load(PROPERTY_SECRET, ENV_SECRET);

    @Override
    public CompletableFuture<MapLinkToken> fetchToken(TokenProvider provider) {
        return provider.getToken(key, secret);
    }

    private String load(String property, String env) {
        return Optional
                .ofNullable(System.getProperty(property, System.getenv(env)))
                .orElseThrow(NoCredentialsProvidedException::new);
    }
}
