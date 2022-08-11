package global.maplink.token;

import global.maplink.credentials.InvalidCredentialsException;
import global.maplink.env.Environment;
import global.maplink.http.HttpAsyncEngine;
import global.maplink.http.Response;
import global.maplink.http.request.PostRequest;
import global.maplink.json.JsonMapper;
import global.maplink.token.OAuthTokenProvider.TokenRequestResponse;
import lombok.SneakyThrows;
import lombok.val;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import static java.time.Instant.now;
import static java.util.concurrent.CompletableFuture.completedFuture;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class OAuthTokenProviderTest {

    private static final Environment ENVIRONMENT = () -> "https://maplink-test.com";

    private static final byte[] MOCK_RESP = new byte[0];

    private static final String CLIENT_ID = "clientId";
    private static final String CLIENT_SECRET = "secret";


    @Test
    @SneakyThrows
    void mustCallHttpEngineWithBodyAndTakeResponse() {
        val createDate = now();
        val expiresIn = 60;
        val resultToken = new MapLinkToken("teste", createDate.plusSeconds(expiresIn));
        val http = mock(HttpAsyncEngine.class);
        when(http.run(any(PostRequest.class)))
                .thenReturn(completedFuture(new Response(200, "application/json", MOCK_RESP)));

        val mapper = mock(JsonMapper.class);
        when(mapper.fromJson(MOCK_RESP, TokenRequestResponse.class))
                .thenReturn(new TokenRequestResponse(mapOf(
                        "access_token", resultToken.getToken(),
                        "expires_in", String.valueOf(expiresIn),
                        "issued_at", String.valueOf(createDate.toEpochMilli())
                )));

        val oauthProvider = new OAuthTokenProvider(http, ENVIRONMENT, mapper);
        val token = oauthProvider.getToken(CLIENT_ID, CLIENT_SECRET).get();

        assertThat(token).isNotSameAs(resultToken);
        assertThat(token.getToken()).isEqualTo(resultToken.getToken());
        assertThat(token.isExpired()).isFalse();
        assertThat(token.isAboutToExpireIn(expiresIn)).isTrue();

        verify(http, times(1)).run(any(PostRequest.class));
        verify(mapper, times(1)).toJson(any());
        verify(mapper, times(1)).fromJson(MOCK_RESP, TokenRequestResponse.class);
    }

    @Test
    @SneakyThrows
    void mustThrowInvalidCredentialsWhenAuthIsNotOk() {
        val http = mock(HttpAsyncEngine.class);
        when(http.run(any(PostRequest.class)))
                .thenReturn(completedFuture(new Response(401, "application/json", MOCK_RESP)));

        val oauthProvider = new OAuthTokenProvider(http, ENVIRONMENT, mock(JsonMapper.class));

        assertThatThrownBy(() -> oauthProvider.getToken(CLIENT_ID, CLIENT_SECRET).get())
                .isInstanceOf(ExecutionException.class)
                .hasCauseInstanceOf(InvalidCredentialsException.class);

        verify(http, times(1)).run(any(PostRequest.class));
    }


    private Map<String, String> mapOf(String... values) {
        assert values.length % 2 == 0;
        val map = new HashMap<String, String>();
        for (int i = 0; i < values.length; i += 2) {
            map.put(values[i], values[i + 1]);
        }
        return map;
    }
}
