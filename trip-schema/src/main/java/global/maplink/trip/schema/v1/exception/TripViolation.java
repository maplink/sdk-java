package global.maplink.trip.schema.v1.exception;

import global.maplink.validations.ValidationViolation;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class TripViolation implements ValidationViolation {

    private final String message;

    private TripViolation(final TripErrorType errorType) {
        this.message = errorType.getMessage();
    }

    public static TripViolation of(final TripErrorType errorType) {
        return new TripViolation(errorType);
    }
}
