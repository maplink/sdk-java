package global.maplink.place.schema.exception;

import global.maplink.validations.ValidationViolation;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class PlaceViolation implements ValidationViolation {

    private final String message;

    private PlaceViolation(final PlaceErrorType errorType) {
        this.message = errorType.getMessage();
    }

    public static PlaceViolation of(final PlaceErrorType errorType) {
        return new PlaceViolation(errorType);
    }

}
