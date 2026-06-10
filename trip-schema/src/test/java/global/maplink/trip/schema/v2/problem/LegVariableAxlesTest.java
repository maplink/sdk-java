package global.maplink.trip.schema.v2.problem;

import global.maplink.trip.schema.v1.exception.TripErrorType;
import global.maplink.validations.ValidationViolation;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class LegVariableAxlesTest {

    @Test
    void shouldReturnViolationMessageWhenFromSiteIdIsNull() {
        LegVariableAxles leg = LegVariableAxles.builder()
                .toSiteId("End")
                .build();

        assertThat(leg.validate())
                .extracting(ValidationViolation::getMessage)
                .containsExactly(TripErrorType.VARIABLE_AXLES_FROMSITEID_EMPTY.getMessage());
    }

    @Test
    void shouldReturnViolationMessageWhenToSiteIdIsNull() {
        LegVariableAxles leg = LegVariableAxles.builder()
                .fromSiteId("Start")
                .build();

        assertThat(leg.validate())
                .extracting(ValidationViolation::getMessage)
                .containsExactly(TripErrorType.VARIABLE_AXLES_TOSITEID_EMPTY.getMessage());
    }

    @Test
    void shouldReturnViolationWhenFromSiteIdAndToSiteIdAreEqual() {
        LegVariableAxles leg = LegVariableAxles.builder()
                .fromSiteId("Start")
                .toSiteId("start")
                .build();

        assertThat(leg.validate())
                .extracting(ValidationViolation::getMessage)
                .containsExactly("fromSiteId Start and toSiteId start cannot be the same");
    }

    @Test
    void shouldNotReturnViolationsWhenSiteIdsAreDistinct() {
        LegVariableAxles leg = LegVariableAxles.builder()
                .fromSiteId("Start")
                .toSiteId("End")
                .build();

        assertThat(leg.validate()).isEmpty();
    }
}
