package global.maplink.planning.schema.commons;

import global.maplink.json.JsonMapper;
import global.maplink.planning.schema.solution.Activity;
import org.junit.jupiter.api.Test;

import static global.maplink.planning.schema.solution.ActivityType.DELIVERY;
import static global.maplink.planning.schema.solution.PositioningType.TO_OPTIMIZE;
import static global.maplink.planning.schema.solution.SequenceType.SITE;
import static global.maplink.planning.testUtils.CommonSampleFiles.ROUTE;
import static org.assertj.core.api.Assertions.assertThat;

class RouteTest {

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

        Activity activity = route.getActivities().get(0);

        assertThat(activity.getActivity()).isEqualTo(DELIVERY);
        assertThat(activity.getTimeWindow().getStart()).isEqualTo(123);
        assertThat(activity.getTimeWindow().getEnd()).isEqualTo(456);
        assertThat(activity.getType()).isEqualTo(SITE);
        assertThat(activity.getSite()).isEqualTo("site1");
        assertThat(activity.getFixedTimeSite()).isEqualTo(10);
        assertThat(activity.getVolume()).isEqualTo(20.0);
        assertThat(activity.getWeight()).isEqualTo(30.0);
        assertThat(activity.getOperations()).hasSize(2);
        assertThat(activity.getOperations()).containsExactlyInAnyOrder("ex1", "ex2");
        assertThat(activity.getArrivalSite()).isEqualTo("arrival1");
        assertThat(activity.getDepartureSite()).isEqualTo("departure1");
        assertThat(activity.getDistance()).isEqualTo(15);
        assertThat(activity.getNominalDuration()).isEqualTo(25);
        assertThat(activity.getPositioningType()).isEqualTo(TO_OPTIMIZE);
        assertThat(activity.getOperationCompartments().get(0).getGroupId()).isEqualTo("ex1");

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

