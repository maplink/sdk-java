package global.maplink.planning.schema.commons;

import global.maplink.json.JsonMapper;
import org.junit.jupiter.api.Test;

import static global.maplink.planning.testUtils.SampleFiles.COMPARTMENT_SOLUTION_GROUP;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

class CompartmentSolutionGroupTest {

    private final JsonMapper mapper = JsonMapper.loadDefault();

    @Test
    void shouldDeserialize(){

        CompartmentSolutionGroup compartmentSolutionGroup = mapper.fromJson(COMPARTMENT_SOLUTION_GROUP.load(), CompartmentSolutionGroup.class);

        CompartmentSolution compartmentSolutionChild0 = compartmentSolutionGroup.getCompartmentSolutions().get(0);
        CompartmentSolution compartmentSolutionChild1 = compartmentSolutionGroup.getCompartmentSolutions().get(1);

        assertThat(compartmentSolutionGroup.getGroupId()).isEqualTo("exemplo1");
        assertThat(compartmentSolutionGroup.getCompartmentSolutions()).hasSize(2);

        assertEquals("exemplo1", compartmentSolutionChild0.getCompartment());
        assertEquals(10.0, compartmentSolutionChild0.getCapacityWeightUsed());
        assertEquals(20.0, compartmentSolutionChild0.getCapacityVolumeUsed());

        assertEquals("exemplo2", compartmentSolutionChild1.getCompartment());
        assertEquals(11.0, compartmentSolutionChild1.getCapacityWeightUsed());
        assertEquals(21.0, compartmentSolutionChild1.getCapacityVolumeUsed());
    }
}
