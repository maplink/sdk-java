package global.maplink.freight.schema.exception;

import global.maplink.validations.ValidationViolation;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class FreightViolation implements ValidationViolation {

    private final String message;

    private FreightViolation(final FreightErrorType errorType) {
        this.message = errorType.getMessage();
    }

    public static FreightViolation of(final FreightErrorType errorType) {
        return new FreightViolation(errorType);
    }

}
