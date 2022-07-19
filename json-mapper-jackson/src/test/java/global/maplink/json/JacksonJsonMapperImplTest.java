package global.maplink.json;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class JacksonJsonMapperImplTest {

    @Test
    public void mustBeAccessibleByLoadDefault() {
        JsonMapper mapper = JsonMapper.loadDefault();
        assertThat(mapper).isNotNull().isInstanceOf(JacksonJsonMapperImpl.class);
    }
}
