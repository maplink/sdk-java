package global.maplink.trip.schema.v1.payload;

import global.maplink.json.JsonMapper;
import org.junit.jupiter.api.Test;

import static global.maplink.trip.testUtils.V1SampleFiles.TRIP_RESPONSE;
import static org.assertj.core.api.Assertions.assertThat;

public class TripResponseTest {
    private final JsonMapper mapper = JsonMapper.loadDefault();

    @Test
    void shouldDeserialize(){
        TripResponse tripResponse = mapper.fromJson(TRIP_RESPONSE.load(), TripResponse.class);
        assertThat(tripResponse.getId()).isEqualTo("DEFAULT_ID");
    }
}
