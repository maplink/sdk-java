package global.maplink.emission.schema.exception;

import global.maplink.validations.ValidationViolation;
import lombok.AllArgsConstructor;
import lombok.Getter;


@Getter
@AllArgsConstructor
public enum EmissionErrorType implements ValidationViolation {
    REQUIRED_FIELDS_INVALID("Required valid field");

    private final String message;
}
