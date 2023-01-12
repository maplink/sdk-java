package global.maplink.token;

import global.maplink.MapLinkSDK;
import global.maplink.credentials.InvalidCredentialsException;
import global.maplink.credentials.MapLinkCredentials;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.concurrent.ExecutionException;

import static global.maplink.env.EnvironmentCatalog.HOMOLOG;
import static global.maplink.helpers.EnvCredentialsHelper.withEnvCredentials;
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
        withEnvCredentials((credentials) -> {
            val token = credentials.fetchToken(MapLinkSDK.getInstance().getTokenProvider()).get();
            assertThat(token).isNotNull();
            assertThat(token.isExpired()).isFalse();
        });
    }

    @Test
    @SneakyThrows
    void mustUseCacheWhenNecessary() {
        withEnvCredentials((credentials) -> {
            TokenProvider tokenProvider = MapLinkSDK.getInstance().getTokenProvider();
            val token = credentials.fetchToken(tokenProvider).get();
            val token2 = credentials.fetchToken(tokenProvider).get();
            assertThat(token).isSameAs(token2);
        });
    }

    @Test
    @SneakyThrows
    void mustUseNotCacheWhenNecessary() {
        withEnvCredentials((credentials) -> {
            MapLinkSDK sdk = MapLinkSDK.getInstance();
            TokenProvider tokenProvider = TokenProvider.create(
                    sdk.getHttp(),
                    sdk.getEnvironment(),
                    sdk.getJsonMapper(),
                    false
            );
            val token = credentials.fetchToken(tokenProvider).get();
            val token2 = credentials.fetchToken(tokenProvider).get();
            assertThat(token).isNotSameAs(token2);
        });
    }
}
