package global.maplink.trip.schema.v1.exception.violations;

import global.maplink.validations.ValidationViolation;
import lombok.Getter;

@Getter
public class VariableAxlesSiteIdNotFoundInProblem implements ValidationViolation {
    private static final String MESSAGE ="siteId %s not found in trip points list.";
    private final String message;

    public VariableAxlesSiteIdNotFoundInProblem(String siteId){
        message = String.format(MESSAGE, siteId);
    }
}
