package global.maplink.planning.schema.problem;

import global.maplink.json.JsonMapper;
import org.junit.jupiter.api.Test;

import static global.maplink.planning.testUtils.SampleFiles.PROBLEM;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ProblemTest {

    private final JsonMapper mapper = JsonMapper.loadDefault();

    @Test
    void shouldDeserialize(){

        Problem problem = mapper.fromJson(PROBLEM.load(), Problem.class);

        assertThat(problem.getClientId()).isEqualTo("client1");
        assertThat(problem.getOptimizationProfile()).isEqualTo("optimization1");
        assertThat(problem.getTripsProfile()).isEqualTo("trips1");
        assertThat(problem.getDefaultDepot()).isEqualTo("default1");
        assertThat(problem.getCalculationMode()).isEqualTo("calculation1");
        assertThat(problem.getStartEnd()).isEqualTo(10);
        assertTrue(problem.getHasSolution());
    }
}

