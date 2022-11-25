package global.maplink.tracking.schema.schema.domain;


import global.maplink.geocode.schema.GeoPoint;
import global.maplink.validations.Validable;
import global.maplink.validations.ValidationViolation;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

import static global.maplink.tracking.schema.schema.errors.ValidationErrorType.TRACKING_DRIVER_GEOPOINT_NOTNULL;
import static java.util.Objects.isNull;

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
            errors.add(TRACKING_DRIVER_GEOPOINT_NOTNULL);
        }
        return errors;
    }

    private boolean isInvalid(final GeoPoint currentLocation) {
        return isNull(currentLocation) || isNull(currentLocation.getLat()) || isNull(currentLocation.getLon());
    }

}
