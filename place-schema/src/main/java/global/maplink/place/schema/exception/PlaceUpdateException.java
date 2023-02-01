package global.maplink.place.schema.exception;

import java.io.Serializable;

public class PlaceUpdateException extends RuntimeException implements Serializable {

    public static PlaceUpdateException of(final String field) {
        return new PlaceUpdateException(field);
    }

    private PlaceUpdateException(final String field) {
        super(String.format("%s: %s", PlaceErrorType.REQUIRED_FIELDS_INVALID.getMessage(), field));
    }
}
