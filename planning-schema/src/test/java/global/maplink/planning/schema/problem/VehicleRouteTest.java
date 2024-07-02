package global.maplink.planning.schema.problem;

import global.maplink.json.JsonMapper;
import org.junit.jupiter.api.Test;

import static global.maplink.planning.testUtils.SampleFiles.VEHICLE_ROUTE;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class VehicleRouteTest {

    private final JsonMapper mapper = JsonMapper.loadDefault();

    @Test
    void shouldDeserialize(){

        VehicleRoute vehicleRoute = mapper.fromJson(VEHICLE_ROUTE.load(), VehicleRoute.class);

        assertEquals("exemplo1", vehicleRoute.getVehicle());
    }
}

