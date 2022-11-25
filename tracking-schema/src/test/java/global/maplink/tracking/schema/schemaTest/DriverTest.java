package global.maplink.tracking.schema.schemaTest;

import global.maplink.tracking.schema.schema.domain.Driver;
import lombok.var;
import org.junit.jupiter.api.Test;

import static global.maplink.tracking.schema.schema.errors.ValidationErrorType.TRACKING_GEOPOINT_NOTNULL;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class DriverTest {


    @Test
    public void isValidateDriverCurrentLocationNull() {
        Driver driver = Driver.builder()
                .name("teste")
                .image("teste image")
                .build();

        var errors = driver.validate();
        assertEquals(TRACKING_GEOPOINT_NOTNULL, errors.get(0));
    }
}
