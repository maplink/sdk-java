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

import static lombok.AccessLevel.PRIVATE;

@Data
@Builder
@AllArgsConstructor(access = PRIVATE)
@NoArgsConstructor(force = true)
public class LogisticConstraint {

    private final String name;
    private final Integer siteUnloadingFixedTime;
    private final Integer siteLoadingFixedTime;
    private final Integer loadingMaxSize;
    private final Integer unloadingMaxSize;
    private final String loadingPositionInTimeWindow;
    private final String unloadingPositionInTimeWindow;
    private final String loadingPositionInRoute;
    private final String unloadingPositionInRoute;
    private final Double loadingTimeFlow;
    private final Double unloadingTimeFlow;

    public List<ValidationViolation> validate() {
        List<ValidationViolation> violations = new LinkedList<>();

        if(FieldValidator.isInvalid(name)){
            violations.add(PlanningUpdateViolation.of("logisticConstraint.name"));
        }

        if(!FieldValidator.isNotNegative(siteUnloadingFixedTime)){
            violations.add(PlanningUpdateViolation.of("logisticConstraint.siteUnloadingFixedTime"));
        }

        if(!FieldValidator.isNotNegative(siteLoadingFixedTime)){
            violations.add(PlanningUpdateViolation.of("logisticConstraint.siteLoadingFixedTime"));
        }

        if(!FieldValidator.isNotNegative(loadingTimeFlow)){
            violations.add(PlanningUpdateViolation.of("logisticConstraint.loadingTimeFlow"));
        }

        if(!FieldValidator.isNotNegative(unloadingTimeFlow)){
            violations.add(PlanningUpdateViolation.of("logisticConstraint.unloadingTimeFlow"));
        }

        return violations;
    }
}
