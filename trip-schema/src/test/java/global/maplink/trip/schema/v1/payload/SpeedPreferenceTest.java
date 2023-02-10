package global.maplink.trip.schema.v1.payload;

import global.maplink.json.JsonMapper;
import org.junit.jupiter.api.Test;

import static global.maplink.trip.testUtils.V1SampleFiles.SPEED_PREFERENCE;
import static org.assertj.core.api.Assertions.assertThat;


public class SpeedPreferenceTest {

    private final JsonMapper mapper = JsonMapper.loadDefault();

    @Test
    public void shouldDeserialize(){
        SpeedPreference speedPreference = mapper.fromJson(SPEED_PREFERENCE.load(), SpeedPreference.class);
        assertThat(speedPreference).isNotNull();
        assertThat(speedPreference.getRoadType()).isInstanceOf(RoadType.class);
        assertThat(speedPreference.getRoadType()).isEqualTo(RoadType.HIGHWAY);
        assertThat(speedPreference.getSpeed()).isEqualTo(90);
        assertThat(speedPreference.getSpeedAtToll()).isEqualTo(50);
    }
}
