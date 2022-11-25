package global.maplink.tracking.schema.schema.domain;

import global.maplink.geocode.schema.Address;
import global.maplink.tracking.schema.schema.exceptions.ValidationErrorType;
import lombok.*;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static global.maplink.tracking.schema.schema.exceptions.ValidationErrorType.*;

@Data
@Builder
@NoArgsConstructor(force = true)
@AllArgsConstructor
public class Order {


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

    public List<ValidationErrorType> validate() {
        List<ValidationErrorType> errors = new ArrayList<>();
        if (isInvalidDescription(description)) {
            errors.add(TRACKING_DESCRIPTION_NOTNULL);
        }
        return errors;
    }

    private boolean isInvalidDescription(final String value) {
        return Objects.isNull(value) || value.trim().isEmpty();
    }
}