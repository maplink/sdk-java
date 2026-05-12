package global.maplink.trip.schema.v2.problem;

import global.maplink.json.JsonMapper;
import global.maplink.trip.schema.v1.exception.TripViolation;
import global.maplink.trip.testUtils.ProblemSampleFiles;
import global.maplink.validations.ValidationException;
import global.maplink.validations.ValidationViolation;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;

import static global.maplink.trip.schema.v2.problem.CalculationMode.THE_FASTEST;
import static global.maplink.trip.schema.v2.problem.VehicleType.*;
import static global.maplink.trip.testUtils.ProblemSampleFiles.*;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.assertj.core.api.Assertions.assertThat;

class TripProblemTest {
    private final JsonMapper mapper = JsonMapper.loadDefault();

    @Test
    void shouldDeserializeWithVehicleType() {
        TripProblem problem = mapper.fromJson(PROBLEM_WITH_VEHICLE_TYPE.load(), TripProblem.class);

        assertThat(problem).isNotNull();
        assertThat(problem.getVehicleType()).isEqualTo(TRUCK_WITH_TWO_DOUBLE_AXLES);
        assertThat(problem.getToll()).isNull();
        assertThat(problem.getPoints()).hasSize(2);
        assertThat(problem.getCalculationMode()).isNotNull();
    }

    @Test
    void shouldValidateWhenVehicleTypeIsValid() {
        TripProblem problem = mapper.fromJson(PROBLEM_WITH_VEHICLE_TYPE.load(), TripProblem.class);
        assertThat(problem.validate()).isEmpty();
    }

    @Test
    void shouldCreateEmptyTripProblem() {
        TripProblem problem = new TripProblem();

        assertThat(problem.getVehicleType()).isEqualTo(TRUCK_WITH_TWO_DOUBLE_AXLES);
        assertThat(problem.getToll()).isNull();
        assertThat(problem.getPoints()).isNull();
        assertThat(problem.getCalculationMode()).isEqualTo(THE_FASTEST);
        assertThat(problem.getRestrictionZones()).isNotNull().isEmpty();
        assertThat(problem.getAvoidanceTypes()).isNotNull().isEmpty();
        assertThat(problem.validate()).isEmpty();
    }

    @ParameterizedTest
    @MethodSource("invalidProblemProvider")
    void shouldRejectInvalidProblem(ProblemSampleFiles sampleFile, String expectedMessage) {
        TripProblem problem = mapper.fromJson(sampleFile.load(), TripProblem.class);
        List<ValidationViolation> violations = problem.validate();

        assertThatThrownBy(problem::throwIfInvalid).isInstanceOf(ValidationException.class);
        assertThat(violations).hasSize(1);
        assertThat(violations.get(0)).isInstanceOf(TripViolation.class);
        assertThat(violations.get(0).getMessage()).isEqualTo(expectedMessage);
    }

    @ParameterizedTest
    @MethodSource("invalidProblemProvider")
    void shouldSerializeViolationMessageCorrectly(ProblemSampleFiles sampleFile, String expectedMessage) {
        TripProblem problem = mapper.fromJson(sampleFile.load(), TripProblem.class);
        String serialized = mapper.toJsonString(problem.validate());
        assertThat(serialized).contains("\"message\":\"" + expectedMessage + "\"");
    }

    static Stream<Arguments> invalidProblemProvider() {
        return Stream.of(
                Arguments.of(PROBLEM_WITH_SINGLE_POINT, "The route must contain at least two site points"),
                Arguments.of(PROBLEM_WITH_TOLL_WITHOUT_VEHICLE_TYPE, "Toll parameters must have a vehicleType")
        );
    }
}
