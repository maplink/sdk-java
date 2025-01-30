package global.maplink.trip.schema.v2.problem;

import global.maplink.json.JsonMapper;
import global.maplink.validations.ValidationViolation;
import org.junit.jupiter.api.Test;

import java.util.List;

import static global.maplink.trip.schema.v1.exception.TripErrorType.ROUTE_POINTS_LESS_THAN_TWO;
import static global.maplink.trip.schema.v2.problem.CalculationMode.THE_FASTEST;
import static global.maplink.trip.schema.v2.problem.VehicleType.*;
import static global.maplink.trip.testUtils.ProblemSampleFiles.*;
import static org.assertj.core.api.Assertions.assertThat;

public class TripProblemTest {
    private final JsonMapper mapper = JsonMapper.loadDefault();

    @Test
    public void shouldDeserializeWithVehicleType() {
        TripProblem problem = mapper.fromJson(PROBLEM_WITH_VEHICLE_TYPE.load(), TripProblem.class);

        assertThat(problem).isNotNull();
        assertThat(problem.getVehicleType()).isEqualTo(TRUCK_WITH_TWO_DOUBLE_AXLES);
        assertThat(problem.getToll()).isNull();
        assertThat(problem.getPoints()).hasSize(2);
        assertThat(problem.getCalculationMode()).isNotNull();
    }

    @Test
    public void shouldValidateWhenVehicleTypeIsValid() {
        TripProblem problem = mapper.fromJson(PROBLEM_WITH_VEHICLE_TYPE.load(), TripProblem.class);

        List<ValidationViolation> errors = problem.validate();

        assertThat(errors).isEmpty();
    }

    @Test
    public void shouldFailValidationWhenBothTollAndVehicleTypeArePresent() {
        TripProblem problem = mapper.fromJson(PROBLEM_WITH_TOLL_AND_VEHICLE_TYPE.load(), TripProblem.class);

        List<ValidationViolation> errors = problem.validate();

        assertThat(errors).hasSize(1);
        assertThat(errors.get(0).getMessage()).isEqualTo("Cannot use vehicleType when toll is present");
    }

    @Test
    public void shouldValidateWithMinimumTwoPoints() {
        TripProblem problem = mapper.fromJson(PROBLEM_WITH_SINGLE_POINT.load(), TripProblem.class);

        List<ValidationViolation> errors = problem.validate();

        assertThat(errors)
                .hasSize(1)
                .contains(ROUTE_POINTS_LESS_THAN_TWO);
    }

    @Test
    public void shouldCreateEmptyTripProblem() {
        TripProblem problem = new TripProblem();

        assertThat(problem.getVehicleType()).isNull();
        assertThat(problem.getToll()).isNull();
        assertThat(problem.getPoints()).isNull();
        assertThat(problem.getCalculationMode()).isEqualTo(THE_FASTEST);
        assertThat(problem.getRestrictionZones()).isNotNull().isEmpty();
        assertThat(problem.getAvoidanceTypes()).isNotNull().isEmpty();
        assertThat(problem.validate()).isEmpty();
    }
}