package global.maplink.planning.schema.commons;

import global.maplink.json.JsonMapper;
import org.junit.jupiter.api.Test;

import static global.maplink.planning.testUtils.CommonSampleFiles.COMPARTMENT_SOLUTION;
import static org.assertj.core.api.Assertions.assertThat;

class CompartmentSolutionTest {

    private final JsonMapper mapper = JsonMapper.loadDefault();

    @Test
    void shouldDeserialize(){

        CompartmentSolution compartmentSolution = mapper.fromJson(COMPARTMENT_SOLUTION.load(), CompartmentSolution.class);

        assertThat(compartmentSolution.getCompartment()).isEqualTo("exemplo1");
        assertThat(compartmentSolution.getCapacityWeightUsed()).isEqualTo(10.0);
        assertThat(compartmentSolution.getCapacityVolumeUsed()).isEqualTo(20.0);
    }
}
