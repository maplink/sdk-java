package global.maplink.trip.schema.v1.exception.violations;

import global.maplink.validations.ValidationViolation;
import lombok.Getter;

@Getter
public class CoincidentFromSiteIdAndToSiteIdViolation implements ValidationViolation {
    private static final String MESSAGE ="fromSiteId %s and toSiteId %s cannot be the same";
    private final String message;

    public CoincidentFromSiteIdAndToSiteIdViolation(String fromSiteId, String toSiteId){
        message = String.format(MESSAGE, fromSiteId, toSiteId);
    }
}
