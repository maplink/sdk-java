package global.maplink.tracking.schema.domain;


import global.maplink.tracking.schema.errors.ValidationErrorType;
import global.maplink.validations.Validable;
import global.maplink.validations.ValidationViolation;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

import static java.util.Objects.isNull;


@Data
@Builder
@NoArgsConstructor(force = true)
@AllArgsConstructor
public class OrderStatus implements Validable {

    private final Value value;
    private final String label;

    @Override
    public List<ValidationViolation> validate() {
        List<ValidationViolation> errors = new ArrayList<>();
        if (isNull(value)) {
            errors.add(ValidationErrorType.TRACKING_STATUS_VALUE_NOTNULL);
        }
        if (isInvalid(label)) {
            errors.add(ValidationErrorType.TRACKING_STATUS_LABEL_NOTNULL);
        }
        return errors;
    }

    private boolean isInvalid(final String label) {
        return isNull(label) || label.trim().isEmpty();
    }

}
