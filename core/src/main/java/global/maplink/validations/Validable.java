package global.maplink.validations;

import java.util.List;

public interface Validable {

    default void throwIfInvalid() {
        List<ValidationViolation> violations = validate();
        if (!violations.isEmpty()) {
            throw new ValidationException(violations);
        }
    }

    List<ValidationViolation> validate();
}
