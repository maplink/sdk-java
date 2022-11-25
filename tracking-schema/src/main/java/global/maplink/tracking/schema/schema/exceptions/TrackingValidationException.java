package global.maplink.tracking.schema.schema.exceptions;

public class TrackingValidationException extends RuntimeException {

    public TrackingValidationException(final ValidationErrorType errorType) {
        super(errorType.getMessage());
    }
}
