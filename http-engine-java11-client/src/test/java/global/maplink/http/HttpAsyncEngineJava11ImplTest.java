package global.maplink.http;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.verification.LoggedRequest;
import global.maplink.http.request.Request;
import global.maplink.http.request.RequestBody;
import lombok.SneakyThrows;
import lombok.val;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.net.URL;

import static com.github.tomakehurst.wiremock.client.WireMock.ok;
import static com.github.tomakehurst.wiremock.client.WireMock.post;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;
import static com.github.tomakehurst.wiremock.matching.RequestPatternBuilder.allRequests;
import static global.maplink.helpers.FutureHelper.await;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class HttpAsyncEngineJava11ImplTest {

    public static final String GOOGLE_COM = "https://www.google.com/";

    public static final String TRIP_SYNC = "v2/calculations";

    public static final String HTTP_1_1 = "HTTP/1.1";

    public static WireMockServer wireMockServer = new WireMockServer(wireMockConfig().dynamicPort());

    @BeforeAll
    static void wireMockServerStart() {
        wireMockServer.start();
        wireMockServer.stubFor(post(TRIP_SYNC)
                .willReturn(ok()
                        .withBody("{}")));
    }

    @AfterEach
    void cleanup() {
        wireMockServer.resetAll();
    }

    @Test
    @SneakyThrows
    void returnsHttpVersionEqualThatIsRequest() {
        val engine = new HttpAsyncEngineJava11Impl();
        val requestBody = RequestBody.Json.of("{}");
        val result = engine.run(Request.post(new URL(wireMockServer.baseUrl()), requestBody));

        assertThat(result.get()).isNotNull();

        val requestsLogged = wireMockServer.findAll(allRequests());
        assertThat(requestsLogged).first().extracting(LoggedRequest::getProtocol).isEqualTo(HTTP_1_1);
    }

    @Test
    @SneakyThrows
    void shouldPutUserAgentHeader() {
        val engine = new HttpAsyncEngineJava11Impl().setUserAgent("my user agent");
        val requestBody = RequestBody.Json.of("{}");
        val result = engine.run(Request.post(new URL(wireMockServer.baseUrl()), requestBody));

        assertThat(result.get()).isNotNull();

        val requestsLogged = wireMockServer.findAll(allRequests());
        assertThat(requestsLogged)
                .first()
                .extracting(h -> h.getHeader("user-agent"))
                .isNotNull()
                .isEqualTo("my user agent");
    }

    @AfterAll
    static void wireMockServerStop() {
        wireMockServer.stop();
    }

    @Test
    void mustBeAccessibleByLoadDefault() {
        HttpAsyncEngine engine = HttpAsyncEngine.loadDefault();
        assertThat(engine).isNotNull().isInstanceOf(HttpAsyncEngineJava11Impl.class);
    }

    @Test
    @SneakyThrows
    void mustBeAbleToDoGetRequest() {
        val engine = new HttpAsyncEngineJava11Impl();
        val result = await(engine.run(Request.get(new URL(GOOGLE_COM))));
        assertThat(result).isNotNull();
        assertThat(result.isOk()).isTrue();
    }

    @Test
    @SneakyThrows
    void mustFailOnUnknownRequest() {
        val engine = new HttpAsyncEngineJava11Impl();

        assertThatThrownBy(() -> engine.run(new UnknownRequest(new URL(GOOGLE_COM))).get())
                .isInstanceOf(UnsupportedOperationException.class);
    }

    private static class UnknownRequest extends Request {

        public UnknownRequest(URL url) {
            super(url);
        }
    }
}
