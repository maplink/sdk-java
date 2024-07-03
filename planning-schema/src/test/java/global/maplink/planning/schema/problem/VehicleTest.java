package global.maplink.planning.schema.problem;

import global.maplink.json.JsonMapper;
import org.junit.jupiter.api.Test;

import static global.maplink.planning.testUtils.SampleFiles.VEHICLE;
import static org.assertj.core.api.Assertions.assertThat;

class VehicleTest {

    private final JsonMapper mapper = JsonMapper.loadDefault();

    @Test
    void shouldDeserialize(){

        Vehicle vehicle = mapper.fromJson(VEHICLE.load(), Vehicle.class);

        assertThat(vehicle.getName()).isEqualTo("exemplo1");
        assertThat(vehicle.getVehicleType()).isEqualTo("exemplo2");
        assertThat(vehicle.getLegislationProfile()).isEqualTo("exemplo3");
        assertThat(vehicle.getPriority()).isEqualTo(10);
        assertThat(vehicle.getLogisticZones()).hasSize(2);
        assertThat(vehicle.getLogisticZones()).containsExactlyInAnyOrder("ex1", "ex2");
        assertThat(vehicle.getAvailablePeriods().get(0).getDepartureSite()).isEqualTo("exemplo1");
        assertThat(vehicle.getAvailablePeriods().get(0).getArrivalSite()).isEqualTo("exemplo2");
        assertThat(vehicle.getAvailablePeriods().get(0).getMaxRoutesNumber()).isEqualTo(20);
        assertThat(vehicle.getAvailablePeriods().get(0).getMaxWorkingTime()).isEqualTo(3);
        assertThat(vehicle.getAvailablePeriods().get(0).getMaxDrivingTime()).isEqualTo(4);
        assertThat(vehicle.getAvailablePeriods().get(0).getTimeWindow().getStart()).isEqualTo(1702897200000L);
        assertThat(vehicle.getAvailablePeriods().get(0).getTimeWindow().getEnd()).isEqualTo(1703538000000L);
    }
}

