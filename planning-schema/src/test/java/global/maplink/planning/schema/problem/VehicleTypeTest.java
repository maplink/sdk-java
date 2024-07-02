package global.maplink.planning.schema.problem;

import global.maplink.json.JsonMapper;
import org.junit.jupiter.api.Test;

import static global.maplink.planning.testUtils.SampleFiles.VEHICLE_TYPE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class VehicleTypeTest {

    private final JsonMapper mapper = JsonMapper.loadDefault();

    @Test
    void shouldDeserialize(){

        VehicleType vehicleType = mapper.fromJson(VEHICLE_TYPE.load(), VehicleType.class);

        assertEquals("exemplo1", vehicleType.getName());
        assertEquals("exemplo2", vehicleType.getCompartmentAccessMode());
        assertEquals(10, vehicleType.getSize());
        assertEquals(11, vehicleType.getMaxSitesNumber());
        assertEquals(20.0, vehicleType.getMaxWeight());
        assertEquals(21.0, vehicleType.getMaxVolume());
        assertThat(vehicleType.getCharacteristics()).hasSize(2);
        assertThat(vehicleType.getCharacteristics()).containsExactlyInAnyOrder("ex1", "ex2");
        assertThat(vehicleType.getCompartmentConfigurations().get(0).getName()).isEqualTo("name1");
        assertThat(vehicleType.getCompartmentConfigurations().get(0).getCompartments()).hasSize(2);
        assertThat(vehicleType.getCompartmentConfigurations().get(0).getCompartments()).containsExactlyInAnyOrder("ex1", "ex2");
    }
}

