package global.maplink.emission.schema.exception;

import global.maplink.validations.ValidationViolation;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class EmissionViolation implements ValidationViolation {

    private final String message;

    private EmissionViolation(final String field) {
        this.message = String.format("%s: %s", EmissionErrorType.REQUIRED_FIELDS_INVALID.getMessage(), field);
    }

    public static EmissionViolation of(final String field) {
        return new EmissionViolation(field);
    }

}