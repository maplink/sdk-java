package global.maplink.planning.schema.problem;

import global.maplink.json.JsonMapper;
import global.maplink.validations.ValidationException;
import lombok.val;
import org.junit.jupiter.api.Test;

import static global.maplink.planning.testUtils.ProblemSampleFiles.COMPARTMENT2;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class CompartmentValidatorTest {

    private final JsonMapper mapper = JsonMapper.loadDefault();

    @Test
    void violationsAreCorrect(){

        Compartment compartment = mapper.fromJson(COMPARTMENT2.load(), Compartment.class);

        assertThat(compartment.validate().get(0).toString()).isEqualTo("PlaceUpdateViolation(message=Required valid field: compartment.loadingRule)");
    }
    @Test
    void shouldValidate() {
        val compartment = Compartment.builder().build();
        assertThat(compartment.validate()).isNotEmpty().hasSize(1);

        assertThatThrownBy(compartment::throwIfInvalid).isInstanceOf(ValidationException.class);
    }
}
