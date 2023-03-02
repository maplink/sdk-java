package global.maplink;

import global.maplink.credentials.MapLinkCredentials;
import global.maplink.env.Environment;
import global.maplink.http.MockHttpAsyncEngine;
import global.maplink.json.MockJsonMapper;
import global.maplink.token.OAuthMapLinkTokenImpl;
import org.junit.jupiter.api.Test;

import java.time.Instant;

import static global.maplink.MapLinkServiceRequestAsyncRunner.proxyFor;
import static java.util.Collections.emptyList;
import static java.util.concurrent.CompletableFuture.completedFuture;
import static org.assertj.core.api.Assertions.assertThat;

class MapLinkServiceRequestAsyncRunnerTest {

    public static final String EXPECTED = "value";

    @Test
    void shouldGenerateProxyThatCallDefaultMethods() {
        SampleServiceRequest proxy = proxyFor(
                SampleServiceRequest.class,
                Environment.loadDefault(),
                new MockHttpAsyncEngine(),
                new MockJsonMapper(),
                (clientId, secret) -> completedFuture(new OAuthMapLinkTokenImpl("", Instant.MAX)),
                MapLinkCredentials.ofKey("test", "test"),
                emptyList()
        );
        assertThat(proxy.getValue()).isEqualTo(EXPECTED);
    }

    interface SampleServiceRequest extends MapLinkServiceRequest<String> {
        default String getValue() {
            return EXPECTED;
        }
    }
}