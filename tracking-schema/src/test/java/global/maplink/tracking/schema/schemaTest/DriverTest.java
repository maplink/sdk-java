package global.maplink.tracking.schema.schemaTest;

import global.maplink.geocode.schema.v2.GeoPoint;
import global.maplink.tracking.schema.domain.Driver;
import lombok.val;
import org.junit.jupiter.api.Test;

import static global.maplink.tracking.schema.errors.ValidationErrorType.TRACKING_DRIVER_CURRENTLOCATION_LATLON_NOTNULL;
import static global.maplink.tracking.schema.errors.ValidationErrorType.TRACKING_DRIVER_CURRENTLOCATION_NOTNULL;
import static java.math.BigDecimal.ONE;
import static org.assertj.core.api.Assertions.assertThat;

public class DriverTest {

    @Test
    public void isValidateDriverCurrentLocationNull() {
        val driver = driverWith(null);

        assertThat(driver.validate())
                .hasSize(1)
                .first()
                .isEqualTo(TRACKING_DRIVER_CURRENTLOCATION_NOTNULL);
    }

    @Test
    public void isValidateDriverCurrentLocationLatNull() {
        val driver = driverWith(GeoPoint.of(null, ONE));

        assertThat(driver.validate())
                .hasSize(1)
                .first()
                .isEqualTo(TRACKING_DRIVER_CURRENTLOCATION_LATLON_NOTNULL);
    }

    @Test
    public void isValidateDriverCurrentLocationLongNull() {
        val driver = driverWith(GeoPoint.of(ONE, null));

        assertThat(driver.validate())
                .hasSize(1)
                .first()
                .isEqualTo(TRACKING_DRIVER_CURRENTLOCATION_LATLON_NOTNULL);
    }

    private Driver driverWith(GeoPoint point) {
        return Driver.builder()
                .name("teste")
                .image("teste image")
                .currentLocation(point)
                .build();
    }
}
