package global.maplink.planning.schema.problem;

import global.maplink.json.JsonMapper;
import org.junit.jupiter.api.Test;

import static global.maplink.planning.testUtils.SampleFiles.VEHICLE_ROUTE;
import static org.assertj.core.api.Assertions.assertThat;

public class VehicleRouteTest {

    private final JsonMapper mapper = JsonMapper.loadDefault();

    @Test
    void shouldDeserialize(){

        VehicleRoute vehicleRoute = mapper.fromJson(VEHICLE_ROUTE.load(), VehicleRoute.class);

        assertThat(vehicleRoute.getVehicle()).isEqualTo("exemplo1");
        assertThat(vehicleRoute.getPeriod().getDepartureSite()).isEqualTo("ex1");
        assertThat(vehicleRoute.getPeriod().getArrivalSite()).isEqualTo("ex2");
        assertThat(vehicleRoute.getPeriod().getMaxRoutesNumber()).isEqualTo(10);
        assertThat(vehicleRoute.getPeriod().getMaxWorkingTime()).isEqualTo(11);
        assertThat(vehicleRoute.getPeriod().getMaxDrivingTime()).isEqualTo(12);
    }
}

