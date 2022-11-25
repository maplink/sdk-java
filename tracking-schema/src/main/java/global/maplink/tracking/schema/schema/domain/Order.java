package global.maplink.tracking.schema.schema.domain;

import global.maplink.geocode.schema.Address;
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

import static global.maplink.tracking.schema.schema.errors.ValidationErrorType.*;
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
        if (isNull(destination.getRoad())) {
            errors.add(TRACKING_DESTINATION_ROAD_NOTNULL);
        }
        if (isNull(destination.getNumber())) {
            errors.add(TRACKING_DESTINATION_NUMBER_NOTNULL);
        }
        if (isNull(destination.getCity())) {
            errors.add(TRACKING_DESTINATION_CITY_NOTNULL);
        }
        if (isNull(destination.getZipCode())) {
            errors.add(TRACKING_DESTINATION_ZIPCODE_NOTNULL);
        }
        if (isNull(destination.getState())) {
            errors.add(TRACKING_DESTINATION_STATE_NOTNULL);
        }
        if (isNull(destination.getMainLocation())) {
            errors.add(TRACKING_GEOPOINT_NOTNULL);
        }
        if (nonNull(driver)) {
            errors.addAll(driver.validate());
        }
        if (nonNull(status)) {
            errors.addAll(status.validate());
        }
        return errors;
    }

    private boolean isInvalidDescription(final String value) {
        return isNull(value) || value.trim().isEmpty();
    }
}