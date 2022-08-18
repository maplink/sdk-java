package global.maplink.http;

import global.maplink.http.request.Request;
import lombok.SneakyThrows;
import lombok.val;
import org.junit.jupiter.api.Test;

import java.net.URL;

import static global.maplink.helpers.FutureHelper.await;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class HttpAsyncEngineJava11ImplTest {

    public static final String GOOGLE_COM = "https://www.google.com/";

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
