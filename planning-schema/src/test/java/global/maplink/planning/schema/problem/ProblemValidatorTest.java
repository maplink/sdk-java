package global.maplink.planning.schema.problem;

import global.maplink.json.JsonMapper;
import global.maplink.validations.ValidationException;
import lombok.val;
import org.junit.jupiter.api.Test;

import static global.maplink.planning.testUtils.ProblemSampleFiles.PROBLEM2;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ProblemValidatorTest {

    private final JsonMapper mapper = JsonMapper.loadDefault();

    @Test
    void violationsAreCorrect(){

        Problem problem = mapper.fromJson(PROBLEM2.load(), Problem.class);

        assertThat(problem.validate().get(0).toString()).isEqualTo("PlaceUpdateViolation(message=Required valid field: problem.optimizationProfile)");
        assertThat(problem.validate().get(1).toString()).isEqualTo("PlaceUpdateViolation(message=Required valid field: problem.tripsProfile)");
    }

    @Test
    void shouldValidate() {
        val problem = Problem.builder().build();
        assertThat(problem.validate()).isNotEmpty().hasSize(2);

        assertThatThrownBy(problem::throwIfInvalid).isInstanceOf(ValidationException.class);
    }

}

