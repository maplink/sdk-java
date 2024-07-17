package global.maplink.planning.schema.problem;

import global.maplink.planning.schema.exception.PlanningUpdateViolation;
import global.maplink.planning.schema.validator.FieldValidator;
import global.maplink.validations.ValidationViolation;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.LinkedList;
import java.util.List;

import static java.util.Objects.isNull;
import static lombok.AccessLevel.PRIVATE;

@Data
@Builder
@AllArgsConstructor(access = PRIVATE)
@NoArgsConstructor(force = true)
public class AvailablePeriod {

    private final TimeWindow timeWindow;
    private final String departureSite;
    private final String arrivalSite;
    private final Integer maxRoutesNumber;
    private final Integer maxWorkingTime;
    private final Integer maxDrivingTime;

    public List<ValidationViolation> validate() {
        List<ValidationViolation> violations = new LinkedList<>();

        if(FieldValidator.isInvalid(departureSite)){
            violations.add(PlanningUpdateViolation.of("availablePeriod.departureSite"));
        }

        if(FieldValidator.isInvalid(arrivalSite)){
            violations.add(PlanningUpdateViolation.of("availablePeriod.arrivalSite"));
        }

        if(FieldValidator.isNotNegative(maxRoutesNumber)){
            violations.add(PlanningUpdateViolation.of("availablePeriod.maxRoutesNumber"));
        }

        if(FieldValidator.isNotNegative(maxWorkingTime)){
            violations.add(PlanningUpdateViolation.of("availablePeriod.maxWorkingTime"));
        }

        if(FieldValidator.isNotNegative(maxDrivingTime)){
            violations.add(PlanningUpdateViolation.of("availablePeriod.maxDrivingTime"));
        }

        if(isNull(timeWindow)){
            violations.add(PlanningUpdateViolation.of("availablePeriod.timeWindow"));
        }

        this.getTimeWindow().validate();

        return violations;
    }

    private boolean isNullOrEmpty(final String value) {
        return isNull(value) || value.trim().isEmpty();
    }
}
