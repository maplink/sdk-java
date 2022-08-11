package global.maplink.token;

import global.maplink.credentials.InvalidCredentialsException;
import global.maplink.env.Environment;
import global.maplink.http.HttpAsyncEngine;
import global.maplink.http.request.PostRequest;
import global.maplink.json.JsonMapper;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.val;

import java.time.Instant;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

import static java.lang.Long.parseLong;

@RequiredArgsConstructor
public class OAuthTokenProvider implements TokenProvider {

    private static final String TOKEN_PATH = "oauth/client_credential/accesstoken?grant_type=client_credentials";

    private final HttpAsyncEngine http;

    private final Environment environment;

    private final JsonMapper mapper;

    @Override
    public CompletableFuture<MapLinkToken> getToken(String clientId, String secret) {
        val body = new TokenRequestBody(clientId, secret);
        return http.run(new PostRequest(
                environment.withService(TOKEN_PATH),
                mapper.toJson(body)
        )).thenApply(r -> {
            if (!r.isOk()) {
                throw new InvalidCredentialsException(clientId, r.parseBodyObject(mapper, String.class));
            }
            return r.parseBodyObject(mapper, TokenRequestResponse.class).buildToken();
        });
    }

    @RequiredArgsConstructor
    @Getter
    static class TokenRequestBody {
        private final String clientId;
        private final String secret;
    }

    @RequiredArgsConstructor
    static class TokenRequestResponse {
        private final Map<String, String> values;

        String getAccessToken() {
            return values.get("access_token");
        }

        Instant getExpiration() {
            long expiresIn = parseLong(values.get("expires_in"));
            long issuedAt = parseLong(values.get("issued_at"));
            return Instant.ofEpochMilli(issuedAt).plusSeconds(expiresIn);
        }

        MapLinkToken buildToken() {
            return new MapLinkToken(getAccessToken(), getExpiration());
        }
    }


}
