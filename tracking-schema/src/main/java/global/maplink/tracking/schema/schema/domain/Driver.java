package global.maplink.tracking.schema.schema.domain;


import global.maplink.geocode.schema.GeoPoint;
import global.maplink.tracking.schema.schema.exceptions.ValidationErrorType;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static global.maplink.tracking.schema.schema.exceptions.ValidationErrorType.*;

@Data
@Builder
@NoArgsConstructor(force = true)
@AllArgsConstructor
public class Driver {

    private final String name;
    private final String image;
    private final GeoPoint currentLocation;

    public List<ValidationErrorType> validate() {
        List<ValidationErrorType> errors = new ArrayList<>();
        if (isInvalid(currentLocation)) {
            errors.add(TRACKING_DRIVER_GEOPOINT_NOTNULL);
        }
        return errors;
    }

    private boolean isInvalid(final GeoPoint currentLocation) {
        return Objects.isNull(currentLocation);
    }

}
