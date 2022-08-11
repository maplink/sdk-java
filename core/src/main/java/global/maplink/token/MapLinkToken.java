package global.maplink.token;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.Instant;

import static java.lang.String.format;
import static java.time.Instant.now;

@RequiredArgsConstructor
@Getter
@EqualsAndHashCode
public class MapLinkToken {

    private final String token;

    private final Instant expiration;

    public boolean isExpired() {
        return expiration.isBefore(now());
    }

    public boolean isAboutToExpireIn(int seconds) {
        if (isExpired()) {
            return true;
        }
        return expiration.isBefore(now().plusSeconds(seconds));
    }

    public String asHeaderValue() {
        return format("Bearer %s", token);
    }
}
