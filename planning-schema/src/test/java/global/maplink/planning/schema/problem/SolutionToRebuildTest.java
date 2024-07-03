package global.maplink.planning.schema.problem;

import global.maplink.json.JsonMapper;
import global.maplink.planning.schema.commons.Route;
import org.junit.jupiter.api.Test;

import static global.maplink.planning.testUtils.SampleFiles.SOLUTION_TO_REBUILD;
import static org.assertj.core.api.Assertions.assertThat;

class SolutionToRebuildTest {

    private final JsonMapper mapper = JsonMapper.loadDefault();

    @Test
    void shouldDeserialize(){

        SolutionToRebuild solutionToRebuild = mapper.fromJson(SOLUTION_TO_REBUILD.load(), SolutionToRebuild.class);

        assertThat(solutionToRebuild.getVehicleRoutes().get(0).getVehicle()).isEqualTo("vehicle1");
        assertThat(solutionToRebuild.getVehicleRoutes().get(0).getPeriod().getDepartureSite()).isEqualTo("departure1");
        assertThat(solutionToRebuild.getVehicleRoutes().get(0).getPeriod().getArrivalSite()).isEqualTo("arrival1");
        assertThat(solutionToRebuild.getVehicleRoutes().get(0).getPeriod().getMaxRoutesNumber()).isEqualTo(10);
        assertThat(solutionToRebuild.getVehicleRoutes().get(0).getPeriod().getMaxWorkingTime()).isEqualTo(11);
        assertThat(solutionToRebuild.getVehicleRoutes().get(0).getPeriod().getMaxDrivingTime()).isEqualTo(12);

        Route route = solutionToRebuild.getVehicleRoutes().get(0).getRoutes().get(0);
        assertThat(route.getId()).isEqualTo("id1");
        assertThat(route.getStatus()).isEqualTo("status1");
        assertThat(route.getTripId()).isEqualTo("tripId1");
        assertThat(route.getCompartmentConfiguration()).isEqualTo("compartment1");
    }
}

