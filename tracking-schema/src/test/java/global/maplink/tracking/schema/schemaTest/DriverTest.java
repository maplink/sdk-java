package global.maplink.tracking.schema.schemaTest;

import global.maplink.geocode.schema.GeoPoint;
import global.maplink.tracking.schema.domain.Driver;
import global.maplink.tracking.schema.errors.TrackingViolation;
import global.maplink.validations.ValidationException;
import global.maplink.validations.ValidationViolation;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Stream;

import static java.math.BigDecimal.ONE;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

public class DriverTest {

    @Test
    void shouldValidate() {
        Driver driver = driverWith(GeoPoint.of(BigDecimal.valueOf(-23.564515), BigDecimal.valueOf(-46.652681)));
        assertThat(driver.validate()).isEmpty();
    }

    @ParameterizedTest
    @MethodSource("invalidDriverProvider")
    void shouldRejectInvalidDriver(Driver driver, String expectedMessage) {
        List<ValidationViolation> violations = driver.validate();

        assertThatThrownBy(driver::throwIfInvalid).isInstanceOf(ValidationException.class);
        assertThat(violations).hasSize(1);
        assertThat(violations.get(0)).isInstanceOf(TrackingViolation.class);
        assertThat(violations.get(0).getMessage()).isEqualTo(expectedMessage);
    }

    static Stream<Arguments> invalidDriverProvider() {
        return Stream.of(
                Arguments.of(driverWith(null), "driver.currentLocation cannot be null"),
                Arguments.of(driverWith(GeoPoint.of(null, ONE)), "at driver.currentLocation lat and lon cannot be null"),
                Arguments.of(driverWith(GeoPoint.of(ONE, null)), "at driver.currentLocation lat and lon cannot be null"),
                Arguments.of(driverWith(GeoPoint.of(null, null)),"at driver.currentLocation lat and lon cannot be null")
        );
    }

    private static Driver driverWith(GeoPoint point) {
        return Driver.builder()
                .name("teste")
                .image("teste image")
                .currentLocation(point)
                .build();
    }
}
