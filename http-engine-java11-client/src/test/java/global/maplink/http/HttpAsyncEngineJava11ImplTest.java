package global.maplink.http;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.verification.LoggedRequest;
import global.maplink.http.request.Request;
import global.maplink.http.request.RequestBody;
import lombok.SneakyThrows;
import lombok.val;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.net.URL;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.github.tomakehurst.wiremock.matching.RequestPatternBuilder.allRequests;
import static global.maplink.helpers.FutureHelper.await;
import static org.assertj.core.api.Assertions.*;

public class HttpAsyncEngineJava11ImplTest {

    public static final String GOOGLE_COM = "https://www.google.com/";

    public static final String TRIP_SYNC = "v2/calculations";

    public static final String HTTP_1_1 = "HTTP/1.1";

    public static WireMockServer wireMockServer = new WireMockServer();

    @BeforeAll
    public static void wireMockServerStart() {
        wireMockServer.start();
        wireMockServer.stubFor(post(TRIP_SYNC)
                .willReturn(ok()
                        .withBody("{}")));
    }

    @Test
    @SneakyThrows
    public void returnsHttpVersionEqualThatIsRequest() {
        val engine = new HttpAsyncEngineJava11Impl();
        val requestBody = RequestBody.Json.of("{}");
        val result = engine.run(Request.post(new URL(wireMockServer.baseUrl()), requestBody));

        assertThat(result.get()).isNotNull();

        val requestsLogged = wireMockServer.findAll(allRequests());
        assertThat(requestsLogged).first().extracting(LoggedRequest::getProtocol).isEqualTo(HTTP_1_1);
    }

    @AfterAll
    public static void wireMockServerStop() {
        wireMockServer.stop();
    }

    @Test
    public void mustBeAccessibleByLoadDefault() {
        HttpAsyncEngine engine = HttpAsyncEngine.loadDefault();
        assertThat(engine).isNotNull().isInstanceOf(HttpAsyncEngineJava11Impl.class);
    }

    @Test
    @SneakyThrows
    public void mustBeAbleToDoGetRequest() {
        val engine = new HttpAsyncEngineJava11Impl();
        val result = await(engine.run(Request.get(new URL(GOOGLE_COM))));
        assertThat(result).isNotNull();
        assertThat(result.isOk()).isTrue();
    }

    @Test
    @SneakyThrows
    public void mustFailOnUnknownRequest() {
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
