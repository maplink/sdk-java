package global.maplink.planning.schema.problem;

import global.maplink.json.JsonMapper;
import org.junit.jupiter.api.Test;

import static global.maplink.planning.testUtils.SampleFiles.ROUTE;
import static org.assertj.core.api.Assertions.assertThat;

public class RouteTest {

    private final JsonMapper mapper = JsonMapper.loadDefault();

    @Test
    void shouldDeserialize(){
        Route route = mapper.fromJson(ROUTE.load(), Route.class);

        assertThat(route.getId()).isEqualTo("exemplo1");
        assertThat(route.getStatus()).isEqualTo("exemplo2");
        assertThat(route.getCompartmentConfiguration()).isEqualTo("exemplo3");
        assertThat(route.getTripId()).isEqualTo("exemplo4");

        assertThat(route.getViolationConstraints()).hasSize(1);
        assertThat(route.getViolationConstraints().get(0).getMessage()).isEqualTo("message1");

        assertThat(route.getActivities().get(0).getActivity()).isEqualTo("activity1");
        assertThat(route.getActivities().get(0).getTimeWindow().getStart()).isEqualTo(123);
        assertThat(route.getActivities().get(0).getTimeWindow().getEnd()).isEqualTo(456);
        assertThat(route.getActivities().get(0).getType()).isEqualTo("type1");
        assertThat(route.getActivities().get(0).getSite()).isEqualTo("site1");
        assertThat(route.getActivities().get(0).getFixedTimeSite()).isEqualTo(10);
        assertThat(route.getActivities().get(0).getVolume()).isEqualTo(20.0);
        assertThat(route.getActivities().get(0).getWeight()).isEqualTo(30.0);
        assertThat(route.getActivities().get(0).getOperations()).hasSize(2);
        assertThat(route.getActivities().get(0).getOperations()).containsExactlyInAnyOrder("ex1", "ex2");
        assertThat(route.getActivities().get(0).getArrivalSite()).isEqualTo("arrival1");
        assertThat(route.getActivities().get(0).getDepartureSite()).isEqualTo("departure1");
        assertThat(route.getActivities().get(0).getDistance()).isEqualTo(15);
        assertThat(route.getActivities().get(0).getNominalDuration()).isEqualTo(25);
        assertThat(route.getActivities().get(0).getPositioningType()).isEqualTo("positioning1");
        assertThat(route.getActivities().get(0).getOperationCompartments().get(0).getGroupId()).isEqualTo("ex1");

        CompartmentSolution compartmentSolution = route.getActivities()
                .get(0)
                .getOperationCompartments()
                .get(0)
                .getCompartmentSolutions()
                .get(0);
        assertThat(compartmentSolution.getCompartment()).isEqualTo("compartment1");
        assertThat(compartmentSolution.getCapacityVolumeUsed()).isEqualTo(12.0);
        assertThat(compartmentSolution.getCapacityWeightUsed()).isEqualTo(11.0);
    }
}

