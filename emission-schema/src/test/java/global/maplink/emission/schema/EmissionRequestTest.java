package global.maplink.emission.schema;

import global.maplink.json.JsonMapper;
import global.maplink.validations.ValidationException;
import lombok.val;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static global.maplink.emission.testUtils.SampleFiles.*;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class EmissionRequestTest {

    private final JsonMapper mapper = JsonMapper.loadDefault();

    @Test
    void shouldSerializeJsonFileToEmissionRequestTest(){
        EmissionRequest emissionRequest = mapper.fromJson(EMISSION_REQUEST.load(), EmissionRequest.class);
        assertEquals("LASTROP_ESALQ", emissionRequest.getSource());
        assertEquals("BIODIESEL", emissionRequest.getFuelType());
        assertEquals(0, new BigDecimal("11.3").compareTo(emissionRequest.getAutonomy()));
        assertEquals(0, new BigDecimal("4.9").compareTo(emissionRequest.getFuelPrice()));
        assertEquals(80000, emissionRequest.getTotalDistance());
    }

    @Test
    void shouldValidate() {
        val emissionRequest = EmissionRequest.builder().build();
        assertThat(emissionRequest.validate()).isNotEmpty().hasSize(3);

        assertThatThrownBy(emissionRequest::throwIfInvalid).isInstanceOf(ValidationException.class);
    }

    @Test
    void shouldNotValidateFuelType() {
        EmissionRequest emissionRequest = mapper.fromJson(
                EMISSION_REQUEST_WITH_NULL_GET_FUEL_TYPE.load(),
                EmissionRequest.class
        );
        assertThatThrownBy(emissionRequest::throwIfInvalid).isInstanceOf(ValidationException.class);
        assertThat(emissionRequest.validate()).isNotEmpty().hasSize(2);
    }

    @Test
    void shouldNotValidateGetTotalDistance() {
        EmissionRequest emissionRequest = mapper.fromJson(
                EMISSION_REQUEST_WITH_NULL_GET_TOTAL_DISTANCE.load(),
                EmissionRequest.class
        );
        assertThatThrownBy(emissionRequest::throwIfInvalid).isInstanceOf(ValidationException.class);
        assertThat(emissionRequest.validate()).isNotEmpty().hasSize(2);
    }

    @Test
    void shouldNotValidateGetAutonomy() {
        EmissionRequest emissionRequest = mapper.fromJson(
                EMISSION_REQUEST_WITH_NULL_GET_AUTONOMY.load(),
                EmissionRequest.class
        );
        assertThatThrownBy(emissionRequest::throwIfInvalid).isInstanceOf(ValidationException.class);
        assertThat(emissionRequest.validate()).isNotEmpty().hasSize(1);
    }
}
