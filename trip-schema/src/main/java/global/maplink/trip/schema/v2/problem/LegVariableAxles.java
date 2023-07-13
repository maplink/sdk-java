package global.maplink.trip.schema.v2.problem;

import global.maplink.toll.schema.TollVehicleType;
import global.maplink.trip.schema.v1.exception.violations.CoincidentFromSiteIdAndToSiteIdViolation;
import global.maplink.validations.Validable;
import global.maplink.validations.ValidationViolation;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

import static global.maplink.trip.schema.v1.exception.TripErrorType.VARIABLE_AXLES_FROMSITEID_EMPTY;
import static global.maplink.trip.schema.v1.exception.TripErrorType.VARIABLE_AXLES_TOSITEID_EMPTY;
import static java.util.Objects.isNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LegVariableAxles implements Validable {
    private String fromSiteId;
    private String toSiteId;
    private TollVehicleType newVehicleType;

    @Override
    public List<ValidationViolation> validate() {
        List<ValidationViolation> errors = new ArrayList<>();

        if (isNull(fromSiteId)){
            errors.add(VARIABLE_AXLES_FROMSITEID_EMPTY);
            return errors;
        }

        if (isNull(toSiteId)){
            errors.add(VARIABLE_AXLES_TOSITEID_EMPTY);
            return errors;
        }

        if (fromSiteId.equalsIgnoreCase(toSiteId)) {
            errors.add(new CoincidentFromSiteIdAndToSiteIdViolation(fromSiteId, toSiteId));
        }

        return errors;
    }
}
