package global.maplink.place.schema.exception;

import global.maplink.json.JsonMapper;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class PlaceViolationTest {

    private final JsonMapper mapper = JsonMapper.loadDefault();

    @Test
    void shouldExposeErrorTypeMessage() {
        PlaceViolation violation = PlaceViolation.of(PlaceErrorType.CATEGORY_SUBCATEGORY_NECESSARY);

        assertThat(violation.getMessage()).isEqualTo(PlaceErrorType.CATEGORY_SUBCATEGORY_NECESSARY.getMessage());
    }

    @Test
    void shouldSerializeAsMessageObject() {
        PlaceViolation violation = PlaceViolation.of(PlaceErrorType.REQUIRED_FIELD_STATE_INVALID);

        assertThat(mapper.toJsonString(violation))
                .isEqualTo("{\"message\":\"" + PlaceErrorType.REQUIRED_FIELD_STATE_INVALID.getMessage() + "\"}");
    }
}
