package global.maplink.trip.schema.problem;

import global.maplink.json.JsonMapper;
import org.junit.jupiter.api.Test;

import static global.maplink.trip.testUtils.ProblemSampleFiles.TOLL_REQUEST;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TollRequestTest {

    private final JsonMapper mapper = JsonMapper.loadDefault();

    @Test
    public void shouldSerialize(){
        TollRequest tollRequest = mapper.fromJson(TOLL_REQUEST.load(), TollRequest.class);
        assertEquals("TRUCK", tollRequest.getVehicleType());
        assertEquals("CREDIT_CARD", tollRequest.getBilling());
    }
}
