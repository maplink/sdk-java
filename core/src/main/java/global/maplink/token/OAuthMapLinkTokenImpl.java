package global.maplink.token;

import global.maplink.http.request.Request;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.Instant;

import static java.lang.String.format;
import static java.time.Instant.now;

@RequiredArgsConstructor
@Getter
@EqualsAndHashCode
public class OAuthMapLinkTokenImpl implements MapLinkToken {

    public static final String BEARER_TEMPLATE = "Bearer %s";
    private final String token;

    private final Instant expiration;

    @Override
    public boolean isExpired() {
        return expiration.isBefore(now());
    }

    @Override
    public boolean isAboutToExpireIn(int seconds) {
        if (isExpired()) {
            return true;
        }
        return expiration.isBefore(now().plusSeconds(seconds));
    }

    @Override
    public Request applyOn(Request request) {
        return request.withAuthorizationHeader(format(BEARER_TEMPLATE, token));
    }

}
