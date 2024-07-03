package global.maplink.planning.schema.problem;

import global.maplink.json.JsonMapper;
import global.maplink.planning.schema.commons.CompartmentSolution;
import global.maplink.planning.schema.commons.CompartmentSolutionGroup;
import org.junit.jupiter.api.Test;

import static global.maplink.planning.testUtils.SampleFiles.ACTIVITY;
import static org.assertj.core.api.Assertions.assertThat;


class ActivityTest {

    private final JsonMapper mapper = JsonMapper.loadDefault();

    @Test
    void shouldDeserialize(){

        Activity activity = mapper.fromJson(ACTIVITY.load(), Activity.class);

        CompartmentSolutionGroup compartmentSolutionGroup = activity.getOperationCompartments().get(0);
        CompartmentSolution compartmentSolutionChild0 = compartmentSolutionGroup.getCompartmentSolutions().get(0);
        CompartmentSolution compartmentSolutionChild1 = compartmentSolutionGroup.getCompartmentSolutions().get(1);

        assertThat(activity.getActivity()).isEqualTo("exemplo1");
        assertThat(activity.getTimeWindow().getStart()).isEqualTo(1702897200000L);
        assertThat(activity.getTimeWindow().getEnd()).isEqualTo(1703538000000L);
        assertThat(activity.getType()).isEqualTo("type1");
        assertThat(activity.getSite()).isEqualTo("site1");
        assertThat(activity.getArrivalSite()).isEqualTo("site2");
        assertThat(activity.getFixedTimeSite()).isEqualTo(5);
        assertThat(activity.getVolume()).isEqualTo(20.0);
        assertThat(activity.getWeight()).isEqualTo(40.0);
        assertThat(activity.getOperations()).hasSize(2).containsExactlyInAnyOrder("ex1", "ex2");
        assertThat(activity.getOperationCompartments()).hasSize(1);

        assertThat(compartmentSolutionGroup.getGroupId()).isEqualTo("exemplo");
        assertThat(compartmentSolutionGroup.getCompartmentSolutions()).hasSize(2);

        assertThat(compartmentSolutionChild0.getCompartment()).isEqualTo("exemplo1");
        assertThat(compartmentSolutionChild0.getCapacityWeightUsed()).isEqualTo(10.0);
        assertThat(compartmentSolutionChild0.getCapacityVolumeUsed()).isEqualTo(20.0);

        assertThat(compartmentSolutionChild1.getCompartment()).isEqualTo("exemplo2");
        assertThat(compartmentSolutionChild1.getCapacityWeightUsed()).isEqualTo(11.0);
        assertThat(compartmentSolutionChild1.getCapacityVolumeUsed()).isEqualTo(21.0);
    }
}