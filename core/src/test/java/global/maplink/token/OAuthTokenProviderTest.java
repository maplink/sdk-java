package global.maplink.token;

import global.maplink.credentials.InvalidCredentialsException;
import global.maplink.env.Environment;
import global.maplink.http.HttpAsyncEngine;
import global.maplink.http.MediaType;
import global.maplink.http.Response;
import global.maplink.http.request.PostRequest;
import global.maplink.json.JsonMapper;
import lombok.SneakyThrows;
import lombok.val;
import org.junit.jupiter.api.Test;

import java.util.Map;
import java.util.concurrent.ExecutionException;

import static global.maplink.helpers.MapHelpers.mapOf;
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
    public static final String RESPONSE_ACCESS_TOKEN = "access_token";
    public static final String RESPONSE_EXPIRES_IN = "expires_in";
    public static final String RESPONSE_ISSUED_AT = "issued_at";

    @Test
    @SneakyThrows
    void mustCallHttpEngineWithBodyAndTakeResponse() {
        val createDate = now();
        val expiresIn = 60;
        val resultToken = new OAuthMapLinkTokenImpl("teste", createDate.plusSeconds(expiresIn));
        val http = mock(HttpAsyncEngine.class);
        when(http.run(any(PostRequest.class)))
                .thenReturn(completedFuture(new Response(200, MediaType.Application.JSON, MOCK_RESP)));

        val mapper = mock(JsonMapper.class);
        when(mapper.fromJson(MOCK_RESP, Map.class))
                .thenReturn(mapOf(
                        RESPONSE_ACCESS_TOKEN, resultToken.getToken(),
                        RESPONSE_EXPIRES_IN, String.valueOf(expiresIn),
                        RESPONSE_ISSUED_AT, String.valueOf(createDate.toEpochMilli())
                ));

        val oauthProvider = new OAuthTokenProvider(http, ENVIRONMENT, mapper);
        val token = oauthProvider.getToken(CLIENT_ID, CLIENT_SECRET).get();
        assertThat(token).isInstanceOf(OAuthMapLinkTokenImpl.class).isNotSameAs(resultToken);
        assertThat(token.isExpired()).isFalse();
        assertThat(token.isAboutToExpireIn(expiresIn)).isTrue();
        val oauthToken = (OAuthMapLinkTokenImpl) token;
        assertThat(oauthToken.getToken()).isEqualTo(resultToken.getToken());

        verify(http, times(1)).run(any(PostRequest.class));
        verify(mapper, times(1)).fromJson(MOCK_RESP, Map.class);
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

}
