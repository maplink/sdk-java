package global.maplink.planning.schema.commons;

import global.maplink.json.JsonMapper;
import org.junit.jupiter.api.Test;

import static global.maplink.planning.testUtils.CommonSampleFiles.COMPARTMENT_SOLUTION_GROUP;
import static org.assertj.core.api.Assertions.assertThat;

class CompartmentSolutionGroupTest {

    private final JsonMapper mapper = JsonMapper.loadDefault();

    @Test
    void shouldDeserialize(){

        CompartmentSolutionGroup compartmentSolutionGroup = mapper.fromJson(COMPARTMENT_SOLUTION_GROUP.load(), CompartmentSolutionGroup.class);

        CompartmentSolution compartmentSolutionChild0 = compartmentSolutionGroup.getCompartmentSolutions().get(0);
        CompartmentSolution compartmentSolutionChild1 = compartmentSolutionGroup.getCompartmentSolutions().get(1);

        assertThat(compartmentSolutionGroup.getGroupId()).isEqualTo("exemplo1");
        assertThat(compartmentSolutionGroup.getCompartmentSolutions()).hasSize(2);

        assertThat(compartmentSolutionChild0.getCompartment()).isEqualTo("exemplo1");
        assertThat(compartmentSolutionChild0.getCapacityWeightUsed()).isEqualTo(10.0);
        assertThat(compartmentSolutionChild0.getCapacityVolumeUsed()).isEqualTo(20.0);

        assertThat(compartmentSolutionChild1.getCompartment()).isEqualTo("exemplo2");
        assertThat(compartmentSolutionChild1.getCapacityWeightUsed()).isEqualTo(11.0);
        assertThat(compartmentSolutionChild1.getCapacityVolumeUsed()).isEqualTo(21.0);
    }
}
