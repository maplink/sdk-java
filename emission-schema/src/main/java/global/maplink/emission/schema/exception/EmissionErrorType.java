package global.maplink.emission.schema.exception;


import global.maplink.validations.ValidationViolation;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum EmissionErrorType implements ValidationViolation {
    REQUIRED_FIELDS_INVALID("Required valid field"),
    REQUIRED_FIELDS_IS_NULL("Required field must not be null"),
    FRACTIONED_EMISSION_BIGGER_THAN_100("Field value must not be bigger than 100"),
    TOTAL_DISTANCE_NEGATIVE("Field value must not be negative");

    private final String message;
}