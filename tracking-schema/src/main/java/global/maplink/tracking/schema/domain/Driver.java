package global.maplink.tracking.schema.domain;


import global.maplink.geocode.schema.GeoPoint;
import global.maplink.validations.Validable;
import global.maplink.validations.ValidationViolation;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

import static global.maplink.tracking.schema.errors.GeoPointValidator.validateGeoPoint;
import static global.maplink.tracking.schema.errors.ValidationErrorType.TRACKING_DRIVER_CURRENTLOCATION_LATLON_NOTNULL;
import static global.maplink.tracking.schema.errors.ValidationErrorType.TRACKING_DRIVER_CURRENTLOCATION_NOTNULL;

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
        return validateGeoPoint(
                currentLocation,
                TRACKING_DRIVER_CURRENTLOCATION_NOTNULL,
                TRACKING_DRIVER_CURRENTLOCATION_LATLON_NOTNULL
        );
    }

}
