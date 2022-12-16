package global.maplink.trip.schema.v2.features.crossedBorders;

import global.maplink.json.JsonMapper;
import org.junit.jupiter.api.Test;

import static global.maplink.trip.schema.v2.features.crossedBorders.CrossedBorderLevel.STATE;
import static global.maplink.trip.schema.v2.features.reverseGeocode.ReverseGeocodePointsMode.START_END_LEGS;
import static global.maplink.trip.testUtils.ProblemSampleFiles.CROSSED_BORDERS_REQUEST;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class CrossedBordersRequestTest {

    private final JsonMapper mapper = JsonMapper.loadDefault();

    @Test
    public void shouldDeserialize() {
        CrossedBordersRequest crossedBordersRequest = mapper.fromJson(CROSSED_BORDERS_REQUEST.load(), CrossedBordersRequest.class);
        assertEquals(STATE, crossedBordersRequest.getLevel());
        assertEquals(START_END_LEGS, crossedBordersRequest.getReverseGeocode());
    }
}
