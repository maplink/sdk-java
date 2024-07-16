package global.maplink.planning.schema.exception;

import global.maplink.validations.ValidationViolation;
import lombok.Getter;
import lombok.ToString;

import static global.maplink.planning.schema.exception.PlanningErrorType.REQUIRED_FIELDS_INVALID;

@Getter
@ToString
public class PlanningUpdateViolation implements ValidationViolation {
    private final String message;

    private PlanningUpdateViolation(final String field) {
        this.message = String.format("%s: %s", REQUIRED_FIELDS_INVALID.getMessage(), field);
    }

    public static PlanningUpdateViolation of(final String field) {
        return new PlanningUpdateViolation(field);
    }
}
