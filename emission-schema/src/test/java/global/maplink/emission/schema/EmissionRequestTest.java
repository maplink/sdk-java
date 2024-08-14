package global.maplink.emission.schema;

import global.maplink.json.JsonMapper;
import global.maplink.validations.ValidationException;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static global.maplink.emission.testUtils.SampleFiles.*;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
        EmissionRequest emissionRequest = mapper.fromJson(EMISSION_REQUEST.load(), EmissionRequest.class);
        assertThat(emissionRequest.validate()).isEmpty();
    }

    @Test
    void shouldValidateFractionedRequestNull() {
        EmissionRequest emissionRequest = mapper.fromJson(EMISSION_REQUEST_WITH_FRACTIONS.load(), EmissionRequest.class);
        assertThat(emissionRequest.validate()).isEmpty();
    }

    @Test
    void shouldValidateFuelTypeNull() {
        EmissionRequest emissionRequest = mapper.fromJson(
                EMISSION_REQUEST_WITH_NULL_FUEL_TYPE.load(),
                EmissionRequest.class
        );
        assertThatThrownBy(emissionRequest::throwIfInvalid).isInstanceOf(ValidationException.class);
        assertThat(emissionRequest.validate()).isNotEmpty().hasSize(1);
        assertThat(emissionRequest.validate().get(0).getMessage()).isEqualTo("Required field must not be null: emission.fuelType");
    }

    @Test
    void shouldValidateTotalDistanceNull() {
        EmissionRequest emissionRequest = mapper.fromJson(
                EMISSION_REQUEST_WITH_NULL_TOTAL_DISTANCE.load(),
                EmissionRequest.class
        );
        assertThatThrownBy(emissionRequest::throwIfInvalid).isInstanceOf(ValidationException.class);
        assertThat(emissionRequest.validate()).isNotEmpty().hasSize(1);
        assertThat(emissionRequest.validate().get(0).getMessage()).isEqualTo("Required field must not be null: emission.totalDistance");
    }

    @Test
    void shouldValidateAutonomyNull() {
        EmissionRequest emissionRequest = mapper.fromJson(
                EMISSION_REQUEST_WITH_NULL_AUTONOMY.load(),
                EmissionRequest.class
        );
        assertThatThrownBy(emissionRequest::throwIfInvalid).isInstanceOf(ValidationException.class);
        assertThat(emissionRequest.validate()).isNotEmpty().hasSize(1);
        assertThat(emissionRequest.validate().get(0).getMessage()).isEqualTo("Required field must not be null: emission.autonomyOrAverageConsumption");
    }

    @Test
    void shouldValidateFractionedEmissionsBiggerThan100() {
        EmissionRequest emissionRequest = mapper.fromJson(
                EMISSION_REQUEST_WITH_WRONG_PERCENTAGES.load(),
                EmissionRequest.class
        );
        assertThatThrownBy(emissionRequest::throwIfInvalid).isInstanceOf(ValidationException.class);
        assertThat(emissionRequest.validate()).isNotEmpty().hasSize(1);
        assertThat(emissionRequest.validate().get(0).getMessage()).isEqualTo("Field value must not be bigger than 100: emission.fractionedEmission");
    }

    @Test
    void shouldValidateTotalDistanceNegative() {
        EmissionRequest emissionRequest = mapper.fromJson(
                EMISSION_REQUEST_WITH_NEGATIVE_TOTAL_DISTANCE.load(),
                EmissionRequest.class
        );
        assertThatThrownBy(emissionRequest::throwIfInvalid).isInstanceOf(ValidationException.class);
        assertThat(emissionRequest.validate()).isNotEmpty().hasSize(1);
        assertThat(emissionRequest.validate().get(0).getMessage()).isEqualTo("Field value must not be negative: emission.totalDistance");
    }
}
