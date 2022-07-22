package global.maplink.credentials;

public interface MapLinkCredentials {
    String getToken();

    static MapLinkCredentials loadDefault() {
        return new EnvMapLinkCredentials();
    }

    static MapLinkCredentials ofToken(String token) {
        return new ProvidedMapLinkCredentials(token);
    }
}
