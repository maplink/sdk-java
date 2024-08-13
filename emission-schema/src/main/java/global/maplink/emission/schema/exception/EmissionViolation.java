package global.maplink.emission.schema.exception;

import global.maplink.validations.ValidationViolation;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class EmissionViolation implements ValidationViolation {

    private final String message;

    private EmissionViolation(final String field, final EmissionErrorType errorType) {
        this.message = String.format("%s: %s", errorType.getMessage(), field);
    }

    public static EmissionViolation of(final String field, EmissionErrorType errorType) {
        return new EmissionViolation(field, errorType);
    }

}