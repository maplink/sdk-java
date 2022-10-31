package global.maplink.trip.schema.v2.problem;

import global.maplink.json.JsonMapper;
import global.maplink.trip.schema.v2.problem.CrossedBordersRequest;
import org.junit.jupiter.api.Test;

import static global.maplink.trip.testUtils.ProblemSampleFiles.CROSSED_BORDERS_REQUEST;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class CrossedBordersRequestTest {

    private final JsonMapper mapper = JsonMapper.loadDefault();

    @Test
    public void shouldSerialize(){
        CrossedBordersRequest crossedBordersRequest = mapper.fromJson(CROSSED_BORDERS_REQUEST.load(), CrossedBordersRequest.class);
        assertEquals("HIGH", crossedBordersRequest.getLevel());
        assertEquals("reverseGeocode", crossedBordersRequest.getReverseGeocode());
    }
}
