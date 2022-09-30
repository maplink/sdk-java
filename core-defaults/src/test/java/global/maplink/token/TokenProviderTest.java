package global.maplink.token;

import global.maplink.MapLinkSDK;
import global.maplink.credentials.InvalidCredentialsException;
import global.maplink.credentials.MapLinkCredentials;
import global.maplink.credentials.NoCredentialsProvidedException;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.concurrent.ExecutionException;

import static global.maplink.env.EnvironmentCatalog.HOMOLOG;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@Slf4j
public class TokenProviderTest {

    @BeforeAll
    static void initializeSDK() {
        MapLinkSDK.configure()
                .with(MapLinkCredentials.ofKey("empty", "empty"))
                .with(HOMOLOG)
                .initialize();
    }

    @AfterAll
    static void cleanupSDK() {
        MapLinkSDK.resetConfiguration();
    }

    @Test
    void mustFailWithInvalidCredentials() {
        val tokenProvider = MapLinkSDK.getInstance().getTokenProvider();
        assertThatThrownBy(() -> tokenProvider.getToken("wrongClient", "wrongSecret").get())
                .isInstanceOf(ExecutionException.class)
                .hasCauseInstanceOf(InvalidCredentialsException.class);
    }

    @Test
    @SneakyThrows
    void mustWorkWithValidCredentials() {
        MapLinkCredentials credentials;
        try {
            credentials = MapLinkCredentials.loadDefault();
        } catch (NoCredentialsProvidedException e) {
            log.warn("Valid credentials not provided, ignoring tests {}", e.getMessage());
            return;
        }

        val token = credentials.fetchToken(MapLinkSDK.getInstance().getTokenProvider()).get();
        assertThat(token).isNotNull();
        assertThat(token.isExpired()).isFalse();
    }
}
