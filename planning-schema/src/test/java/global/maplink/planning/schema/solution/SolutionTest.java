package global.maplink.planning.schema.solution;

import global.maplink.json.JsonMapper;
import org.junit.jupiter.api.Test;

import static global.maplink.planning.testUtils.SampleFiles.SOLUTION;
import static org.assertj.core.api.Assertions.assertThat;


class SolutionTest {

    private final JsonMapper mapper = JsonMapper.loadDefault();

    @Test
    void shouldDeserialize(){

        Solution solution = mapper.fromJson(SOLUTION.load(), Solution.class);

        assertThat(solution.getClientId()).isEqualTo("exemplo1");

        assertThat(solution.getVehicleRoutes().get(0).getVehicle()).isEqualTo("exemplo1");
        assertThat(solution.getVehicleRoutes().get(0).getPeriod().getDepartureSite()).isEqualTo("ex1");
        assertThat(solution.getVehicleRoutes().get(0).getPeriod().getArrivalSite()).isEqualTo("ex2");
        assertThat(solution.getVehicleRoutes().get(0).getPeriod().getMaxRoutesNumber()).isEqualTo(10);
        assertThat(solution.getVehicleRoutes().get(0).getPeriod().getMaxWorkingTime()).isEqualTo(11);
        assertThat(solution.getVehicleRoutes().get(0).getPeriod().getMaxDrivingTime()).isEqualTo(12);
        assertThat(solution.getVehicleRoutes().get(0).getPeriod().getTimeWindow().getStart()).isEqualTo(10);
        assertThat(solution.getVehicleRoutes().get(0).getPeriod().getTimeWindow().getEnd()).isEqualTo(20);

        assertThat(solution.getRejectOperations()).hasSize(2);
        assertThat(solution.getRejectOperations()).containsExactlyInAnyOrder("ex1", "ex2");

        //assertThat(solution.getPossibleCauseOfRejectOperationsGroup().get(0).getGroupId()).isEqualTo("ex1");
        //assertThat(solution.getPossibleCauseOfRejectOperationsGroup().get(0).getPossibleCauseRejects().get(0).getCode()).isEqualTo("exemplo1");
        //assertThat(solution.getPossibleCauseOfRejectOperationsGroup().get(0).getPossibleCauseRejects().get(0).getMessage()).isEqualTo("exemplo2");

        assertThat(solution.getPossibleCauseOfRejectOperationsGroup()).isNull();
    }
}