package global.maplink.trip.schema.v1.exception;

import java.io.Serializable;

public class TripCalculationRequestException extends RuntimeException implements Serializable {
    public TripCalculationRequestException(TripErrorType errorType) {
        super(errorType.getMessage());
    }
}

