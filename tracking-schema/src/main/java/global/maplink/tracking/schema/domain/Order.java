package global.maplink.tracking.schema.domain;

import global.maplink.geocode.schema.v1.Address;
import global.maplink.validations.Validable;
import global.maplink.validations.ValidationViolation;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static global.maplink.tracking.schema.errors.GeoPointValidator.validateGeoPoint;
import static global.maplink.tracking.schema.errors.ValidationErrorType.*;
import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

@Data
@Builder
@NoArgsConstructor(force = true)
@AllArgsConstructor
public class Order implements Validable {

    private final String id;
    private final Long number;
    private final String description;
    private final LocalDateTime estimatedArrival;
    private final String companyName;
    private final OrderValue totalValue;
    private final OrderStatus status;
    private final Address origin;
    private final Address destination;
    private final Driver driver;
    private final Audit audit;
    private final Instant expiresIn;
    private final String theme;

    @Override
    public List<ValidationViolation> validate() {
        List<ValidationViolation> errors = new ArrayList<>();

        if (isInvalidDescription(description)) {
            errors.add(TRACKING_DESCRIPTION_NOTNULL);
        }

        if (nonNull(origin)) {
            errors.addAll(validate(origin));
        }

        if (isNull(destination)) {
            errors.add(TRACKING_DESTINATION_NOTNULL);
        }else{
            errors.addAll(validate(destination));
        }

        if (nonNull(driver)) {
            errors.addAll(driver.validate());
        }

        if (nonNull(status)) {
            errors.addAll(status.validate());
        }

        return errors;
    }

    private List<ValidationViolation> validate(Address address) {
        List<ValidationViolation> violations = new ArrayList<>();
        if (isNull(address)) {
            return violations;
        }

        if (isNull(address.getMainLocation())) {
            violations.add(TRACKING_ADDRESS_MAINLOCATION_NOTNULL);
        } else {
            violations.addAll(validateGeoPoint(
                    address.getMainLocation(),
                    TRACKING_ADDRESS_MAINLOCATION_NOTNULL,
                    TRACKING_ADDRESS_MAINLOCATION_LATLON_NOTNULL

            ));
        }
        return violations;
    }

    private boolean isInvalidDescription(final String value) {
        return isNull(value) || value.trim().isEmpty();
    }
}