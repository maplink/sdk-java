package global.maplink.freight.schema.exception;

import java.io.Serializable;

public class FreightRequestException extends RuntimeException implements Serializable {

    public FreightRequestException(final FreightErrorType errorType) {
        super(errorType.getMessage());
    }

}
