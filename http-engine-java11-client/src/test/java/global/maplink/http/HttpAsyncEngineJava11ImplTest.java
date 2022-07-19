package global.maplink.http;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class HttpAsyncEngineJava11ImplTest {

    @Test
    public void mustBeAccessibleByLoadDefault() {
        HttpAsyncEngine engine = HttpAsyncEngine.loadDefault();
        assertThat(engine).isNotNull().isInstanceOf(HttpAsyncEngineJava11Impl.class);
    }
}
