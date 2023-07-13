package global.maplink.trip.schema.v1.exception.violations;

import global.maplink.validations.ValidationViolation;
import lombok.Getter;

@Getter
public class VariableAxlesOverlappingViolation implements ValidationViolation {
    private static final String MESSAGE ="VariableAxles leg overlap found on leg from %s to %s.";
    private final String message;

    public VariableAxlesOverlappingViolation(String fromSiteId, String toSiteId){
        message = String.format(MESSAGE, fromSiteId, toSiteId);
    }
}
