package global.maplink.trip.schema.v1.exception;

import global.maplink.json.JsonMapper;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class TripViolationTest {

    private final JsonMapper mapper = JsonMapper.loadDefault();

    @Test
    void shouldExposeErrorTypeMessage() {
        TripViolation violation = TripViolation.of(TripErrorType.PROFILE_NAME_EMPTY);

        assertThat(violation.getMessage()).isEqualTo(TripErrorType.PROFILE_NAME_EMPTY.getMessage());
    }

    @Test
    void shouldSerializeAsMessageObject() {
        TripViolation violation = TripViolation.of(TripErrorType.ROUTE_POINTS_LESS_THAN_TWO);

        assertThat(mapper.toJsonString(violation))
                .isEqualTo("{\"message\":\"" + TripErrorType.ROUTE_POINTS_LESS_THAN_TWO.getMessage() + "\"}");
    }
}
