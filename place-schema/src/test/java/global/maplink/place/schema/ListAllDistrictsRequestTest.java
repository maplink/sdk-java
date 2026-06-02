package global.maplink.place.schema;

import global.maplink.place.schema.exception.PlaceErrorType;
import global.maplink.validations.ValidationViolation;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ListAllDistrictsRequestTest {

    @Test
    void shouldReturnViolationMessagesWhenStateAndCityAreNull() {
        ListAllDistrictsRequest request = ListAllDistrictsRequest.builder().build();

        assertThat(request.validate())
                .extracting(ValidationViolation::getMessage)
                .containsExactlyInAnyOrder(
                        PlaceErrorType.REQUIRED_FIELD_STATE_INVALID.getMessage(),
                        PlaceErrorType.REQUIRED_FIELD_CITY_INVALID.getMessage()
                );
    }

    @Test
    void shouldReturnViolationMessageWhenOnlyCityIsNull() {
        ListAllDistrictsRequest request = ListAllDistrictsRequest.builder().state("SP").build();

        assertThat(request.validate())
                .extracting(ValidationViolation::getMessage)
                .containsExactly(PlaceErrorType.REQUIRED_FIELD_CITY_INVALID.getMessage());
    }

    @Test
    void shouldNotReturnViolationsWhenStateAndCityArePresent() {
        ListAllDistrictsRequest request = ListAllDistrictsRequest.builder()
                .state("SP")
                .city("São Paulo")
                .build();

        assertThat(request.validate()).isEmpty();
    }
}
