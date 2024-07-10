package global.maplink.planning.schema.problem;

import global.maplink.json.JsonMapper;
import global.maplink.validations.ValidationException;
import lombok.val;
import org.junit.jupiter.api.Test;

import static global.maplink.planning.testUtils.ProblemSampleFiles.PROBLEM;
import static global.maplink.trip.schema.v2.problem.CalculationMode.THE_FASTEST;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ProblemTest {

    private final JsonMapper mapper = JsonMapper.loadDefault();

    @Test
    void shouldDeserialize(){

        Problem problem = mapper.fromJson(PROBLEM.load(), Problem.class);

        assertThat(problem.getClientId()).isEqualTo("client1");
        assertThat(problem.getOptimizationProfile()).isEqualTo("optimization1");
        assertThat(problem.getTripsProfile()).isEqualTo("trips1");
        assertThat(problem.getDefaultDepot()).isEqualTo("default1");
        assertThat(problem.getCalculationMode()).isEqualTo(THE_FASTEST);
        assertThat(problem.getStartEnd()).isEqualTo(10);
        assertThat(problem.getHasSolution()).isTrue();
        assertThat(problem.getOptimizationProfile()).isNotNull();
    }

    @Test
    void shouldValidate() {
        val problem = Problem.builder().build();
        assertThat(problem.validate()).isNotEmpty().hasSize(2);

        assertThatThrownBy(problem::throwIfInvalid).isInstanceOf(ValidationException.class);
    }
}

