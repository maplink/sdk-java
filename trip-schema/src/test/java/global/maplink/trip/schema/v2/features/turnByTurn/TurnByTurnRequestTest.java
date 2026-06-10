package global.maplink.trip.schema.v2.features.turnByTurn;

import global.maplink.trip.schema.v1.exception.TripErrorType;
import global.maplink.validations.ValidationViolation;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class TurnByTurnRequestTest {

    @Test
    void shouldReturnViolationMessageWhenLanguageIsNull() {
        TurnByTurnRequest request = TurnByTurnRequest.builder().build();

        assertThat(request.validate())
                .extracting(ValidationViolation::getMessage)
                .containsExactly(TripErrorType.TURN_BY_TURN_LANGUAGE_NOT_FOUND.getMessage());
    }

    @Test
    void shouldNotReturnViolationsWhenLanguageIsPresent() {
        TurnByTurnRequest request = TurnByTurnRequest.builder()
                .language(Languages.PT_BR)
                .build();

        assertThat(request.validate()).isEmpty();
    }
}
