package global.maplink.place.schema.exception;

import java.io.Serializable;

public class PlaceUpdateException extends RuntimeException implements Serializable {

    public static PlaceUpdateException of(final String field) {
        return new PlaceUpdateException(field);
    }

    private PlaceUpdateException(final String field) {
        super(String.format("%s: %s", ErrorType.PLACE_0007.getMessage(), field));
    }
}
