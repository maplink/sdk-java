package global.maplink.emission.utils;

import global.maplink.credentials.MapLinkCredentials;
import global.maplink.credentials.NoCredentialsProvidedException;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
@Slf4j
public class EnvCredentialsHelper {
    public static void withEnvCredentials(ThrowingConsumer<MapLinkCredentials> action) {
        MapLinkCredentials credentials;
        try {
            credentials = MapLinkCredentials.loadDefault();
        } catch (NoCredentialsProvidedException e) {
            log.warn("Valid credentials not provided, ignoring tests {}", e.getMessage());
            return;
        }
        try {
            action.accept(credentials);
        } catch (Throwable e) {
            if (e instanceof RuntimeException) {
                throw (RuntimeException) e;
            } else {
                throw new RuntimeException(e);
            }
        }
    }

}
