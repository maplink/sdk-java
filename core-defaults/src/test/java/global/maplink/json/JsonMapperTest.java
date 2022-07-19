package global.maplink.json;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class JsonMapperTest {

    @Test
    public void mustLoadDefault() {
        JsonMapper mapper = JsonMapper.loadDefault();
        assertThat(mapper).isNotNull();
    }
}
