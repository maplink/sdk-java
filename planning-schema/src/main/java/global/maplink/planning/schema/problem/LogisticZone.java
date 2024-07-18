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
public class LogisticZone {

    private final String name;
    private final LogisticZoneType zonePriority;

    private final FieldValidator fieldValidator;

    public List<ValidationViolation> validate() {
        List<ValidationViolation> violations = new LinkedList<>();

        if(FieldValidator.isInvalid(name)){
            violations.add(PlanningUpdateViolation.of("logisticZone.name"));
        }

        if(FieldValidator.isInvalid(String.valueOf(zonePriority))){
            violations.add(PlanningUpdateViolation.of("logisticZone.zonePriority"));
        }

        fieldValidator.isContainedIn(violations, zonePriority);

        return violations;
    }
}
