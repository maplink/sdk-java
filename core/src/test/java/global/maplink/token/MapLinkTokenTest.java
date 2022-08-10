package global.maplink.token;

import lombok.val;
import org.junit.jupiter.api.Test;

import java.time.Instant;

import static org.assertj.core.api.Assertions.assertThat;

public class MapLinkTokenTest {

    private static final String SAMPLE_TOKEN = "tokenTest123";

    @Test
    void mustCheckIfIsExpired() {
        val token = new MapLinkToken(
                SAMPLE_TOKEN,
                Instant.now().minusSeconds(1)
        );

        assertThat(token.getToken()).isEqualTo(SAMPLE_TOKEN);
        assertThat(token.isExpired()).isTrue();
        assertThat(token.isAboutToExpireIn(0)).isTrue();
        assertThat(token.isAboutToExpireIn(10)).isTrue();
    }

    @Test
    void mustCheckIfIsAboutToExpire() {
        val token = new MapLinkToken(
                SAMPLE_TOKEN,
                Instant.now().plusSeconds(10)
        );

        assertThat(token.getToken()).isEqualTo(SAMPLE_TOKEN);
        assertThat(token.isExpired()).isFalse();
        assertThat(token.isAboutToExpireIn(15)).isTrue();
        assertThat(token.isAboutToExpireIn(5)).isFalse();
    }

}
