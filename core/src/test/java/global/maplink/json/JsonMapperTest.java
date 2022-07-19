package global.maplink.json;

import global.maplink.NoImplementationFoundException;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class JsonMapperTest {

    @Test
    public void loadDefaultMustFailWithoutImpl() {
        assertThatThrownBy(JsonMapper::loadDefault)
                .isInstanceOf(NoImplementationFoundException.class)
                .hasFieldOrPropertyWithValue("target", JsonMapper.class);
    }
}
