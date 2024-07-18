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
public class IncompabilityRelationship {

    private final String name;
    private final String incompabilityGroup1;
    private final String incompabilityGroup2;
    private final IncompatibilityType type;
    private final List<String> vehicles;

    private final FieldValidator fieldValidator;

    public List<ValidationViolation> validate() {
        List<ValidationViolation> violations = new LinkedList<>();

        if(FieldValidator.isInvalid(name)){
            violations.add(PlanningUpdateViolation.of("incompabilityRelationship.name"));
        }

        if(FieldValidator.isInvalid(incompabilityGroup1)){
            violations.add(PlanningUpdateViolation.of("incompabilityRelationship.incompabilityGroup1"));
        }

        if(FieldValidator.isInvalid(incompabilityGroup2)){
            violations.add(PlanningUpdateViolation.of("incompabilityRelationship.incompabilityGroup2"));
        }

        if(FieldValidator.isInvalid(type.toString())){
            violations.add(PlanningUpdateViolation.of("incompabilityRelationship.type"));
        }

        if(vehicles.isEmpty()){
            violations.add(PlanningUpdateViolation.of("incompabilityRelationship.vehicles"));
        }

        fieldValidator.isContainedIn(violations, type);

        return violations;
    }
}
