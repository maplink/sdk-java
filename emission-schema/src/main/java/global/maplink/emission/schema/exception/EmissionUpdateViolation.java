package global.maplink.emission.schema.exception;

import global.maplink.validations.ValidationViolation;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class EmissionUpdateViolation implements ValidationViolation {

    private final String message;

    private EmissionUpdateViolation(final String field) {
        this.message = String.format("%s: %s", EmissionErrorType.REQUIRED_FIELDS_INVALID.getMessage(), field);
    }

    public static EmissionUpdateViolation of(final String field) {
        return new EmissionUpdateViolation(field);
    }

}