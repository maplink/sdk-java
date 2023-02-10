package global.maplink.trip.schema.v1.payload;

import global.maplink.json.JsonMapper;
import org.junit.jupiter.api.Test;

import static global.maplink.trip.testUtils.V1SampleFiles.CALLBACK;
import static org.assertj.core.api.Assertions.assertThat;


public class CallbackTest {
    private final JsonMapper mapper = JsonMapper.loadDefault();

    @Test
    public void shouldDeserialize(){
        Callback callback = mapper.fromJson(CALLBACK.load(), Callback.class);
        assertThat(callback).isNotNull();
        assertThat(callback.getUrl()).isEqualTo("http://localhost");
        assertThat(callback.getUser()).isEqualTo("user");
        assertThat(callback.getPassword()).isEqualTo("password");
    }
}