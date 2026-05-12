package global.maplink.freight.schema.exception;

import global.maplink.validations.ValidationViolation;
import lombok.Getter;
import lombok.ToString;

import static global.maplink.freight.schema.exception.FreightErrorType.REQUIRED_FIELDS_EMPTY;

@Getter
@ToString
public class FreightViolation implements ValidationViolation {

    private final String message;

    private FreightViolation(final String field) {
        this.message = String.format("%s: %s", REQUIRED_FIELDS_EMPTY.getMessage(), field);
    }

    public static FreightViolation of(final String field) {
        return new FreightViolation(field);
    }
}
