package global.maplink.tracking.schema.schema.exceptions;

public class TrackingException extends RuntimeException {

    public TrackingException(final ErrorType errorType) {
        super(errorType.getMessage());
    }
}
