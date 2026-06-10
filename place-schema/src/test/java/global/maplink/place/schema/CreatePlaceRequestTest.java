package global.maplink.place.schema;

import global.maplink.place.schema.exception.PlaceErrorType;
import global.maplink.validations.ValidationViolation;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class CreatePlaceRequestTest {

    @Test
    void shouldReturnViolationMessageWhenPlaceIsNull() {
        CreatePlaceRequest request = new CreatePlaceRequest(null);

        assertThat(request.validate())
                .extracting(ValidationViolation::getMessage)
                .containsExactly(PlaceErrorType.REQUIRED_FIELDS_INVALID.getMessage());
    }

    @Test
    void shouldDelegateValidationToPlaceWhenPlaceIsPresent() {
        CreatePlaceRequest request = new CreatePlaceRequest(Place.builder().build());

        assertThat(request.validate()).isNotEmpty();
    }
}
