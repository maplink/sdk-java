package global.maplink.tracking.schema.errors;

import global.maplink.geocode.schema.v1.GeoPoint;
import global.maplink.validations.ValidationViolation;
import lombok.NoArgsConstructor;

import java.util.List;

import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;
import static java.util.Objects.isNull;
import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public class GeoPointValidator {

    public static List<ValidationViolation> validateGeoPoint(
            GeoPoint geoPoint,
            ValidationErrorType nullLocationError,
            ValidationErrorType nullLatLonError
    ) {
        if (isNull(geoPoint)) {
            return singletonList(nullLocationError);
        }
        if (isNull(geoPoint.getLat()) || isNull(geoPoint.getLon())) {
            return singletonList(nullLatLonError);
        }
        return emptyList();
    }
}
