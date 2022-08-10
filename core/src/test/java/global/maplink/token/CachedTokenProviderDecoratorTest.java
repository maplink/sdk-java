package global.maplink.token;

import lombok.val;
import org.junit.jupiter.api.Test;

import java.util.concurrent.ExecutionException;
import java.util.stream.IntStream;

import static java.lang.Thread.sleep;
import static java.time.Instant.now;
import static java.util.concurrent.CompletableFuture.completedFuture;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

public class CachedTokenProviderDecoratorTest {

    private static final String CLIENT_ID = "clientId";
    private static final String CLIENT_SECRET = "secret";


    @Test
    void mustCallDelegateOnlyOnceBeforeExpiration() throws ExecutionException, InterruptedException {
        val delegate = mock(TokenProvider.class);
        when(delegate.getToken(CLIENT_ID, CLIENT_SECRET))
                .thenAnswer(i -> completedFuture(new MapLinkToken(
                        "Token",
                        now().plusSeconds(60)
                )));

        val cached = new CachedTokenProviderDecorator(delegate);
        val first = cached.getToken(CLIENT_ID, CLIENT_SECRET).get();
        IntStream.range(0, 10).forEach(i -> cached.getToken(CLIENT_ID, CLIENT_SECRET));
        val second = cached.getToken(CLIENT_ID, CLIENT_SECRET).get();

        assertThat(first).isSameAs(second);
        verify(delegate, times(1)).getToken(CLIENT_ID, CLIENT_SECRET);
    }

    @Test
    void mustCallDelegateAfterExpiration() throws ExecutionException, InterruptedException {
        val delegate = mock(TokenProvider.class);
        when(delegate.getToken(CLIENT_ID, CLIENT_SECRET))
                .thenAnswer(i -> completedFuture(new MapLinkToken(
                        "Token",
                        now().plusMillis(100)
                )));

        val cached = new CachedTokenProviderDecorator(delegate);
        val first = cached.getToken(CLIENT_ID, CLIENT_SECRET).get();

        verify(delegate, times(1)).getToken(CLIENT_ID, CLIENT_SECRET);
        sleep(150);
        val second = cached.getToken(CLIENT_ID, CLIENT_SECRET).get();

        assertThat(first).isNotSameAs(second);
        verify(delegate, times(2)).getToken(CLIENT_ID, CLIENT_SECRET);
    }
}
