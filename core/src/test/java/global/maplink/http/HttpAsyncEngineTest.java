package global.maplink.http;

import global.maplink.NoImplementationFoundException;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class HttpAsyncEngineTest {

    @Test
    public void loadDefaultMustFailWithoutImpl() {
        assertThatThrownBy(HttpAsyncEngine::loadDefault)
                .isInstanceOf(NoImplementationFoundException.class)
                .hasFieldOrPropertyWithValue("target", HttpAsyncEngine.class);
    }
}
