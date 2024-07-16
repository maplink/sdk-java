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
public class CompartmentConfiguration {

    private final String name;
    private final List<Compartment> compartments;

    public List<ValidationViolation> validate() {
        List<ValidationViolation> violations = new LinkedList<>();

        if(isNullOrEmpty(name)){
            violations.add(PlanningUpdateViolation.of("compartmentConfiguration.name"));
        }

        return violations;
    }

    private boolean isNullOrEmpty(final String value) {
        return isNull(value) || value.trim().isEmpty();
    }
}
