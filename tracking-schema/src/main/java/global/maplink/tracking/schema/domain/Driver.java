package global.maplink.tracking.schema.domain;


import global.maplink.geocode.schema.GeoPoint;
import global.maplink.tracking.schema.errors.ValidationErrorType;
import global.maplink.validations.Validable;
import global.maplink.validations.ValidationViolation;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Data
@Builder
@NoArgsConstructor(force = true)
@AllArgsConstructor
public class Driver implements Validable {

    private final String name;
    private final String image;
    private final GeoPoint currentLocation;

    @Override
    public List<ValidationViolation> validate() {
        List<ValidationViolation> errors = new ArrayList<>();
        if (isInvalid(currentLocation)) {
            errors.add(ValidationErrorType.TRACKING_GEOPOINT_NOTNULL);
        }
        return errors;
    }

    private boolean isInvalid(final GeoPoint currentLocation) {
        return Objects.isNull(currentLocation);
    }

}
