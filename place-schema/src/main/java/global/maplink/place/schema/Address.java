package global.maplink.place.schema;

import global.maplink.domain.MaplinkPoint;
import global.maplink.place.schema.exception.PlaceUpdateViolation;
import global.maplink.validations.Validable;
import global.maplink.validations.ValidationViolation;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.util.LinkedList;
import java.util.List;

import static java.util.Objects.isNull;
import static lombok.AccessLevel.PRIVATE;

@Data
@Builder
@RequiredArgsConstructor
@NoArgsConstructor(force = true, access = PRIVATE)
public class Address implements Validable {
    private final String street;
    private final String number;
    private final String district;
    private final String city;
    private final String state;
    private final String zipcode;
    private final String complement;
    private final MaplinkPoint point;

    @Override
    public List<ValidationViolation> validate() {
        List<ValidationViolation> violations = new LinkedList<>();
        if (isInvalid(street)) {
            violations.add(PlaceUpdateViolation.of("address.street"));
        }

        if (isInvalid(city)) {
            violations.add(PlaceUpdateViolation.of("address.city"));
        }

        if (isInvalid(state)) {
            violations.add(PlaceUpdateViolation.of("address.state"));
        }

        if (isInvalid(number)) {
            violations.add(PlaceUpdateViolation.of("address.number"));
        }

        if (isInvalid(zipcode)) {
            violations.add(PlaceUpdateViolation.of("address.zipcode"));
        }

        if (isNull(point)) {
            violations.add(PlaceUpdateViolation.of("address.point"));
        }

        return violations;
    }

    private boolean isInvalid(final String value) {
        return isNull(value) || value.trim().isEmpty();
    }
}
