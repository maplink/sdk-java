package global.maplink.trip.schema.v1.payload;

import global.maplink.json.JsonMapper;
import global.maplink.trip.schema.v1.exception.TripViolation;
import global.maplink.validations.ValidationException;
import global.maplink.validations.ValidationViolation;
import org.junit.jupiter.api.Test;

import java.util.List;

import static global.maplink.trip.schema.v1.payload.LoadType.*;
import static global.maplink.trip.testUtils.V1SampleFiles.INVALID_VEHICLE_SPECIFICATION;
import static global.maplink.trip.testUtils.V1SampleFiles.VALID_VEHICLE_SPECIFICATION;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.assertj.core.api.Assertions.assertThat;

public class VehicleSpecificationTest {
    private final JsonMapper mapper = JsonMapper.loadDefault();

    @Test
    void shouldDeserialize(){
        VehicleSpecification vehicleSpecification = mapper.fromJson(VALID_VEHICLE_SPECIFICATION.load(), VehicleSpecification.class);
        assertThat(vehicleSpecification).isNotNull();
        assertThat(vehicleSpecification.getMaxWeight()).isEqualTo(10000);
        assertThat(vehicleSpecification.getMaxWeightPerAxle()).isEqualTo(2000);
        assertThat(vehicleSpecification.getMaxLength()).isEqualTo(40);
        assertThat(vehicleSpecification.getMaxLengthBetweenAxles()).isEqualTo(5);
        assertThat(vehicleSpecification.getMaxWidth()).isEqualTo(50);
        assertThat(vehicleSpecification.getMaxHeight()).isEqualTo(5);
        assertThat(vehicleSpecification.getMaxWeightForExplodingMaterials()).isEqualTo(30);
        assertThat(vehicleSpecification.getMaxWeightForPollutingMaterials()).isEqualTo(30);
        assertThat(vehicleSpecification.getMaxWeightForDangerousMaterials()).isEqualTo(30);
        assertThat(vehicleSpecification.getLoadTypes()).containsExactlyInAnyOrder(BUILDING_PRODUCTS, HEATH_WASTES, SAND_GRAVELS);
    }

    @Test
    void shouldValidateReturnEmptyErrorArray(){
        VehicleSpecification vehicleSpecification = mapper.fromJson(VALID_VEHICLE_SPECIFICATION.load(), VehicleSpecification.class);
        assertThat(vehicleSpecification.validate()).isEmpty();
    }

    @Test
    void shouldRejectVehicleSpecificationWithNegativeValue() {
        VehicleSpecification vehicleSpecification = mapper.fromJson(INVALID_VEHICLE_SPECIFICATION.load(), VehicleSpecification.class);
        List<ValidationViolation> violations = vehicleSpecification.validate();

        assertThatThrownBy(vehicleSpecification::throwIfInvalid).isInstanceOf(ValidationException.class);
        assertThat(violations).hasSize(1);
        assertThat(violations.get(0)).isInstanceOf(TripViolation.class);
        assertThat(violations.get(0).getMessage()).isEqualTo("VehicleSpecification should not contain any measure with a negative value");
    }
}
