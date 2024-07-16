package global.maplink.planning.schema.exception;

import global.maplink.validations.ValidationViolation;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum PlanningErrorType implements ValidationViolation {
    REQUIRED_FIELDS_INVALID("Required valid field"),
    START_EQUALS_END("Start is equal to end"),
    START_IS_BIGGER_THAN_END("Start is bigger than end");

    private final String message;
}
