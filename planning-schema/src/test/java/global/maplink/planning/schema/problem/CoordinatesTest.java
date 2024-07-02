package global.maplink.planning.schema.problem;

import global.maplink.json.JsonMapper;
import org.junit.jupiter.api.Test;

import static global.maplink.planning.testUtils.SampleFiles.COORDINATES;
import static org.assertj.core.api.Assertions.assertThat;

public class CoordinatesTest {

    private final JsonMapper mapper = JsonMapper.loadDefault();

    @Test
    void shouldDeserialize(){

        Coordinates coordinates = mapper.fromJson(COORDINATES.load(), Coordinates.class);

        assertThat(coordinates.getLongitude()).isEqualTo(11.1);
        assertThat(coordinates.getLatitude()).isEqualTo(22.2);
    }
}
