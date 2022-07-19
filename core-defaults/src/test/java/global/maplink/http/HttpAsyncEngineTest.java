package global.maplink.http;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class HttpAsyncEngineTest {

    @Test
    public void mustLoadDefault() {
        HttpAsyncEngine engine = HttpAsyncEngine.loadDefault();
        assertThat(engine).isNotNull();
    }
}
