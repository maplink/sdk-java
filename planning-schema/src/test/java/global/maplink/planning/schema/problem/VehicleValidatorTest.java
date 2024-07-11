package global.maplink.planning.schema.problem;

import global.maplink.json.JsonMapper;
import global.maplink.validations.ValidationException;
import lombok.val;
import org.junit.jupiter.api.Test;

import static global.maplink.planning.testUtils.ProblemSampleFiles.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class VehicleValidatorTest {

    private final JsonMapper mapper = JsonMapper.loadDefault();

    @Test
    void violationsAreCorrect(){

        Vehicle vehicle = mapper.fromJson(VEHICLE2.load(), Vehicle.class);

        assertThat(vehicle.validate().get(0).toString()).isEqualTo("PlaceUpdateViolation(message=Required valid field: vehicle.name)");
        assertThat(vehicle.validate().get(1).toString()).isEqualTo("PlaceUpdateViolation(message=Required valid field: vehicle.vehicleType)");
        assertThat(vehicle.validate().get(2).toString()).isEqualTo("PlaceUpdateViolation(message=Required valid field: vehicle.legislationProfile)");
        assertThat(vehicle.validate().get(3).toString()).isEqualTo("PlaceUpdateViolation(message=Required valid field: vehicle.priority)");
        assertThat(vehicle.validate().get(4).toString()).isEqualTo("PlaceUpdateViolation(message=Required valid field: vehicle.availablePeriods)");
        assertThat(vehicle.validate().get(5).toString()).isEqualTo("PlaceUpdateViolation(message=Required valid field: vehicle.logisticZones)");
    }

    @Test
    void shouldValidate() {
        val vehicle = Vehicle.builder().build();
        assertThat(vehicle.validate()).isNotEmpty().hasSize(6);

        assertThatThrownBy(vehicle::throwIfInvalid).isInstanceOf(ValidationException.class);
    }
}

