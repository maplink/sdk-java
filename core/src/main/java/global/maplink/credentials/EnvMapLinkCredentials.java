package global.maplink.credentials;

import lombok.Getter;

import java.util.Optional;

@Getter
public class EnvMapLinkCredentials implements MapLinkCredentials {

    private static final String PROPERTY_KEY = "maplink.token";
    private static final String ENV_KEY = "MAPLINK_TOKEN";
    private final String token = Optional
            .ofNullable(System.getProperty(PROPERTY_KEY, System.getenv(ENV_KEY)))
            .orElseThrow(NoCredentialsProvidedException::new);

    @Override
    public String getToken() {
        return token;
    }
}
