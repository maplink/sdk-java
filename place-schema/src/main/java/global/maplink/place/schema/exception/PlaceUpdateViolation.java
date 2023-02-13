package global.maplink.place.schema.exception;

import global.maplink.validations.ValidationViolation;
import lombok.Getter;

import static global.maplink.place.schema.exception.PlaceErrorType.REQUIRED_FIELDS_INVALID;

@Getter
public class PlaceUpdateViolation implements ValidationViolation {

    private final String message;

    private PlaceUpdateViolation(final String field) {
        this.message = String.format("%s: %s", REQUIRED_FIELDS_INVALID.getMessage(), field);
    }

    public static PlaceUpdateViolation of(final String field) {
        return new PlaceUpdateViolation(field);
    }
}
