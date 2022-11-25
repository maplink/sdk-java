package global.maplink.tracking.schema.schema.domain;


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
public class OrderStatus {

    private final Value value;
    private final String label;

    public List<ValidationErrorType> validate() {
        List<ValidationErrorType> errors = new ArrayList<>();
        if (isInvalid(value)) {
            errors.add(TRACKING_STATUS_VALUE_NOTNULL);
        }
        if (isInvalid(label)) {
            errors.add(TRACKING_STATUS_LABEL_NOTNULL);
        }
        return errors;
    }

    private boolean isInvalid(final Object value) {
        return Objects.isNull(value);
    }

    private boolean isInvalid(final String label) {
        return Objects.isNull(label) || label.trim().isEmpty();
    }


}
