package global.maplink.trip.schema.v2.problem;

import global.maplink.commons.TransponderOperator;
import global.maplink.json.JsonMapper;
import global.maplink.toll.schema.Billing;
import global.maplink.toll.schema.TollVehicleType;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.HashSet;

import static global.maplink.trip.testUtils.ProblemSampleFiles.TOLL_REQUEST;
import static global.maplink.trip.testUtils.ProblemSampleFiles.TOLL_REQUEST_CONECTCAR;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TollRequestTest {

    private final JsonMapper mapper = JsonMapper.loadDefault();

    @Test
    public void shouldDeserialize() {
        TollRequest tollRequest = mapper.fromJson(TOLL_REQUEST_CONECTCAR.load(), TollRequest.class);
        assertEquals(TollVehicleType.TRUCK_WITH_TWO_SINGLE_AXIS, tollRequest.getVehicleType());
        assertEquals(Billing.FREE_FLOW, tollRequest.getBilling());
        assertEquals(new HashSet<>(Collections.singletonList(TransponderOperator.CONECTCAR)), tollRequest.getTransponderOperators());
    }

    @Test
    public void shouldDeserializeWithDefaultTransponderOperator() {
        TollRequest tollRequest = mapper.fromJson(TOLL_REQUEST.load(), TollRequest.class);
        assertEquals(TollVehicleType.TRUCK_WITH_TWO_SINGLE_AXIS, tollRequest.getVehicleType());
        assertEquals(Billing.FREE_FLOW, tollRequest.getBilling());
        assertEquals(new HashSet<>(Collections.singletonList(TransponderOperator.SEM_PARAR)), tollRequest.getTransponderOperators());
    }
}
