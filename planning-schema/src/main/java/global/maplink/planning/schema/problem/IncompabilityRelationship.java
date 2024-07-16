package global.maplink.planning.schema.problem;

import global.maplink.planning.schema.exception.PlanningUpdateViolation;
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
public class IncompabilityRelationship {

    private final String name;
    private final String incompabilityGroup1;
    private final String incompabilityGroup2;
    private final IncompatibilityType type;
    private final List<String> vehicles;

    public List<ValidationViolation> validate() {
        List<ValidationViolation> violations = new LinkedList<>();

        if(isNullOrEmpty(name)){
            violations.add(PlanningUpdateViolation.of("incompabilityRelationship.name"));
        }

        if(isNullOrEmpty(incompabilityGroup1)){
            violations.add(PlanningUpdateViolation.of("incompabilityRelationship.incompabilityGroup1"));
        }

        if(isNullOrEmpty(incompabilityGroup2)){
            violations.add(PlanningUpdateViolation.of("incompabilityRelationship.incompabilityGroup2"));
        }

        if(isNullOrEmpty(type.toString())){
            violations.add(PlanningUpdateViolation.of("incompabilityRelationship.type"));
        }

        if(vehicles.isEmpty()){
            violations.add(PlanningUpdateViolation.of("incompabilityRelationship.vehicles"));
        }

        return violations;
    }

    private boolean isNullOrEmpty(final String value) {
        return isNull(value) || value.trim().isEmpty();
    }
}
