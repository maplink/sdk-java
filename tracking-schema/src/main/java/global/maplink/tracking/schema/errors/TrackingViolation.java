package global.maplink.tracking.schema.errors;

import global.maplink.validations.ValidationViolation;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class TrackingViolation implements ValidationViolation {

    private final String message;

    private TrackingViolation(final ValidationErrorType errorType) {
        this.message = errorType.getMessage();
    }

    public static TrackingViolation of(final ValidationErrorType errorType) {
        return new TrackingViolation(errorType);
    }

}
