package global.maplink.planning.schema.problem;

import global.maplink.json.JsonMapper;
import org.junit.jupiter.api.Test;

import static global.maplink.planning.testUtils.ProblemSampleFiles.VEHICLE_TYPE;
import static org.assertj.core.api.Assertions.assertThat;

class VehicleTypeTest {

    private final JsonMapper mapper = JsonMapper.loadDefault();

    @Test
    void shouldDeserialize(){

        VehicleType vehicleType = mapper.fromJson(VEHICLE_TYPE.load(), VehicleType.class);

        assertThat(vehicleType.getName()).isEqualTo("exemplo1");
        assertThat(vehicleType.getCompartmentAccessMode()).isEqualTo("exemplo2");
        assertThat(vehicleType.getSize()).isEqualTo(10);
        assertThat(vehicleType.getMaxSitesNumber()).isEqualTo(11);
        assertThat(vehicleType.getMaxWeight()).isEqualTo(20.0);
        assertThat(vehicleType.getMaxVolume()).isEqualTo(21.0);
        assertThat(vehicleType.getCharacteristics()).hasSize(2);
        assertThat(vehicleType.getCharacteristics()).containsExactlyInAnyOrder("ex1", "ex2");
        assertThat(vehicleType.getCompartmentConfigurations().get(0).getName()).isEqualTo("name1");
        assertThat(vehicleType.getCompartmentConfigurations().get(0).getCompartments()).hasSize(1);
    }
}

