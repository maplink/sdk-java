package global.maplink.tracking.schema.errors;

import global.maplink.json.JsonMapper;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class TrackingViolationTest {

    private final JsonMapper mapper = JsonMapper.loadDefault();

    @Test
    void shouldExposeErrorTypeMessage() {
        TrackingViolation violation = TrackingViolation.of(ValidationErrorType.THEME_ID_NOTNULL);

        assertThat(violation.getMessage()).isEqualTo(ValidationErrorType.THEME_ID_NOTNULL.getMessage());
    }

    @Test
    void shouldSerializeAsMessageObject() {
        TrackingViolation violation = TrackingViolation.of(ValidationErrorType.TRACKING_STATUS_VALUE_NOTNULL);

        assertThat(mapper.toJsonString(violation))
                .isEqualTo("{\"message\":\"" + ValidationErrorType.TRACKING_STATUS_VALUE_NOTNULL.getMessage() + "\"}");
    }
}
