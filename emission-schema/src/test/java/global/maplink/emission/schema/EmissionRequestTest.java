package global.maplink.emission.schema;

import global.maplink.emission.testUtils.SampleFiles;
import global.maplink.json.JsonMapper;
import global.maplink.validations.ValidationException;
import global.maplink.validations.ValidationViolation;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Stream;

import static global.maplink.emission.testUtils.SampleFiles.*;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class EmissionRequestTest {

    private final JsonMapper mapper = JsonMapper.loadDefault();

    @Test
    void shouldSerializeJsonFileToEmissionRequestTest() {
        EmissionRequest emissionRequest = mapper.fromJson(EMISSION_REQUEST.load(), EmissionRequest.class);
        assertEquals("LASTROP_ESALQ", emissionRequest.getSource());
        assertEquals("BIODIESEL", emissionRequest.getFuelType());
        assertEquals(0, new BigDecimal("11.3").compareTo(emissionRequest.getAutonomy()));
        assertEquals(0, new BigDecimal("4.9").compareTo(emissionRequest.getFuelPrice()));
        assertEquals(80000, emissionRequest.getTotalDistance());
    }

    @Test
    void shouldDeserializeFractionedEmission() {
        EmissionRequest request = mapper.fromJson(EMISSION_REQUEST_WITH_FRACTIONS.load(), EmissionRequest.class);

        assertThat(request.getFractionedEmission()).hasSize(2);
        assertThat(request.getFractionedEmission().get(0).getName()).isEqualTo("Cliente");
        assertThat(request.getFractionedEmission().get(0).getPercentage()).isEqualTo(20);
        assertThat(request.getFractionedEmission().get(1).getName()).isEqualTo("Transportadora");
        assertThat(request.getFractionedEmission().get(1).getPercentage()).isEqualTo(80);
    }

    @ParameterizedTest
    @MethodSource("validRequestProvider")
    void shouldValidate(SampleFiles sampleFile) {
        EmissionRequest request = mapper.fromJson(sampleFile.load(), EmissionRequest.class);
        assertThat(request.validate()).isEmpty();
    }

    @ParameterizedTest
    @MethodSource("invalidRequestProvider")
    void shouldRejectInvalidRequest(SampleFiles sampleFile, String expectedMessage) {
        EmissionRequest request = mapper.fromJson(sampleFile.load(), EmissionRequest.class);
        List<ValidationViolation> violations = request.validate();

        assertThatThrownBy(request::throwIfInvalid).isInstanceOf(ValidationException.class);
        assertThat(violations).hasSize(1);
        assertThat(violations.get(0).getMessage()).isEqualTo(expectedMessage);
    }

    @ParameterizedTest
    @MethodSource("invalidRequestProvider")
    void shouldSerializeViolationMessageCorrectly(SampleFiles sampleFile, String expectedMessage) {
        EmissionRequest request = mapper.fromJson(sampleFile.load(), EmissionRequest.class);
        String serialized = mapper.toJsonString(request.validate());
        assertThat(serialized).contains("\"message\":\"" + expectedMessage + "\"");
    }

    static Stream<Arguments> validRequestProvider() {
        return Stream.of(
                Arguments.of(EMISSION_REQUEST),
                Arguments.of(EMISSION_REQUEST_WITH_FRACTIONS),
                Arguments.of(EMISSION_REQUEST_WITH_AVERAGE_CONSUMPTION),
                Arguments.of(EMISSION_REQUEST_WITH_ZERO_TOTAL_DISTANCE)
        );
    }

    static Stream<Arguments> invalidRequestProvider() {
        return Stream.of(
                Arguments.of(EMISSION_REQUEST_WITH_NULL_FUEL_TYPE,             "Required field must not be null: emission.fuelType"),
                Arguments.of(EMISSION_REQUEST_WITH_NULL_TOTAL_DISTANCE,        "Required field must not be null: emission.totalDistance"),
                Arguments.of(EMISSION_REQUEST_WITH_NULL_AUTONOMY,              "Required field must not be null: emission.autonomyOrAverageConsumption"),
                Arguments.of(EMISSION_REQUEST_WITH_WRONG_PERCENTAGES,          "Field value must not be bigger than 100: emission.fractionedEmission"),
                Arguments.of(EMISSION_REQUEST_WITH_NEGATIVE_TOTAL_DISTANCE,    "Field value must not be negative: emission.totalDistance")
        );
    }
}
