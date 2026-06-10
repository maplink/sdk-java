package global.maplink.freight.schema.exception;

import global.maplink.json.JsonMapper;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class FreightViolationTest {

    private final JsonMapper mapper = JsonMapper.loadDefault();

    @Test
    void shouldExposeErrorTypeMessage() {
        FreightViolation violation = FreightViolation.of(FreightErrorType.DATE_FIELD_EMPTY);

        assertThat(violation.getMessage()).isEqualTo(FreightErrorType.DATE_FIELD_EMPTY.getMessage());
    }

    @Test
    void shouldSerializeAsMessageObject() {
        FreightViolation violation = FreightViolation.of(FreightErrorType.AXIS_FIELD_EMPTY);

        assertThat(mapper.toJsonString(violation))
                .isEqualTo("{\"message\":\"" + FreightErrorType.AXIS_FIELD_EMPTY.getMessage() + "\"}");
    }
}
