package global.maplink.token;

import global.maplink.credentials.InvalidCredentialsException;
import global.maplink.env.Environment;
import global.maplink.http.HttpAsyncEngine;
import global.maplink.http.request.Request;
import global.maplink.http.request.RequestBody;
import global.maplink.json.JsonMapper;
import lombok.RequiredArgsConstructor;

import java.time.Instant;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

import static java.lang.Long.parseLong;

@RequiredArgsConstructor
public class OAuthTokenProvider implements TokenProvider {

    private static final String TOKEN_PATH = "oauth/client_credential/accesstoken";
    public static final String PARAM_CLIENT_ID = "client_id";
    public static final String PARAM_CLIENT_SECRET = "client_secret";
    public static final String PARAM_GRANT_TYPE = "grant_type";
    public static final String PARAM_GRANT_TYPE_VALUE = "client_credentials";
    public static final String RESPONSE_ACCESS_TOKEN = "access_token";
    public static final String RESPONSE_EXPIRES_IN = "expires_in";
    public static final String RESPONSE_ISSUED_AT = "issued_at";

    private final HttpAsyncEngine http;

    private final Environment environment;

    private final JsonMapper mapper;

    @SuppressWarnings("unchecked")
    @Override
    public CompletableFuture<MapLinkToken> getToken(String clientId, String secret) {
        return http.run(
                Request.post(
                        environment.withService(TOKEN_PATH),
                        RequestBody.Form.of(
                                PARAM_CLIENT_ID, clientId,
                                PARAM_CLIENT_SECRET, secret
                        )
                ).withQuery(PARAM_GRANT_TYPE, PARAM_GRANT_TYPE_VALUE)
        ).thenApply(r -> {
            if (!r.isOk()) {
                throw new InvalidCredentialsException(
                        clientId,
                        Optional.ofNullable(r.parseBodyObject(mapper, Map.class,false))
                                .map(Map::toString)
                                .orElse("")
                );
            }
            return new TokenRequestResponse(r.parseBodyObject(mapper, Map.class)).buildToken();
        });
    }

    @RequiredArgsConstructor
    static class TokenRequestResponse {
        private final Map<String, String> values;

        String getAccessToken() {
            return values.get(RESPONSE_ACCESS_TOKEN);
        }

        Instant getExpiration() {
            long expiresIn = parseLong(values.get(RESPONSE_EXPIRES_IN));
            long issuedAt = parseLong(values.get(RESPONSE_ISSUED_AT));
            return Instant.ofEpochMilli(issuedAt).plusSeconds(expiresIn);
        }

        MapLinkToken buildToken() {
            return new OAuthMapLinkTokenImpl(getAccessToken(), getExpiration());
        }
    }

}
