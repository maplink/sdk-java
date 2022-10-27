package global.maplink.trip.schema.problem;

import global.maplink.json.JsonMapper;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static global.maplink.trip.testUtils.ProblemSampleFiles.VEHICLE_SPECIFICATION;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class VehicleSpecificationTest {

    private final JsonMapper mapper = JsonMapper.loadDefault();

    @Test
    public void shouldSerialize(){
        VehicleSpecification vehicleSpecification = mapper.fromJson(VEHICLE_SPECIFICATION.load(), VehicleSpecification.class);
        assertEquals(1000.0D, vehicleSpecification.getMaxWeight());
        assertEquals(150.0D, vehicleSpecification.getMaxWeightPerAxle());
        assertEquals(20.0D, vehicleSpecification.getMaxLength());
        assertEquals(50.0D, vehicleSpecification.getMaxLengthBetweenAxles());
        assertEquals(7.0D, vehicleSpecification.getMaxWidth());
        assertEquals(4.0D, vehicleSpecification.getMaxHeight());
        assertEquals(10.0D, vehicleSpecification.getMaxWeightForExplodingMaterials());
        assertEquals(5.0D, vehicleSpecification.getMaxWeightForPollutingMaterials());
        assertEquals(3.0D, vehicleSpecification.getMaxWeightForDangerousMaterials());
        assertTrue(vehicleSpecification.getLoadTypes().containsAll(Arrays.asList("LoadTypeOne", "LoadTypeTwo")));
    }
}
