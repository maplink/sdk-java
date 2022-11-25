package global.maplink.validations;

import java.util.List;

public interface Validable {

    default void throwIfInvalid() {
        throw new ValidationException(validate());
    }

    List<ValidationViolation> validate();
}
