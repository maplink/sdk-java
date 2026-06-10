package global.maplink.place.schema;

import global.maplink.place.schema.exception.PlaceErrorType;
import global.maplink.validations.ValidationViolation;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ListAllCitiesRequestTest {

    @Test
    void shouldReturnViolationMessageWhenStateIsNull() {
        ListAllCitiesRequest request = ListAllCitiesRequest.builder().build();

        assertThat(request.validate())
                .extracting(ValidationViolation::getMessage)
                .containsExactly(PlaceErrorType.REQUIRED_FIELD_STATE_INVALID.getMessage());
    }

    @Test
    void shouldNotReturnViolationsWhenStateIsPresent() {
        ListAllCitiesRequest request = ListAllCitiesRequest.builder().state("SP").build();

        assertThat(request.validate()).isEmpty();
    }
}
