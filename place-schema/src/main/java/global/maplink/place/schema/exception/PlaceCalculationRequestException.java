package global.maplink.place.schema.exception;

import java.io.Serializable;

public class PlaceCalculationRequestException extends RuntimeException implements Serializable {
    public PlaceCalculationRequestException(final ErrorType errorType) {
        super(errorType.getMessage());
    }
}
