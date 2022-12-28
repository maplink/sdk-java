package global.maplink.trip.schema.v2.problem;

import global.maplink.json.JsonMapper;
import global.maplink.toll.schema.TollConditionBillingType;
import global.maplink.toll.schema.TollVehicleType;
import org.junit.jupiter.api.Test;

import static global.maplink.trip.testUtils.ProblemSampleFiles.TOLL_REQUEST;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TollRequestTest {

    private final JsonMapper mapper = JsonMapper.loadDefault();

    @Test
    public void shouldDeserialize(){
        TollRequest tollRequest = mapper.fromJson(TOLL_REQUEST.load(), TollRequest.class);
        assertEquals(TollVehicleType.TRUCK_WITH_TWO_SINGLE_AXIS, tollRequest.getVehicleType());
        assertEquals(TollConditionBillingType.NORMAL, tollRequest.getBilling());
    }
}
