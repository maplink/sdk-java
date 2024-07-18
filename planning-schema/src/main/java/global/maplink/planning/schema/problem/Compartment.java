package global.maplink.planning.schema.problem;

import global.maplink.planning.schema.exception.PlanningUpdateViolation;
import global.maplink.planning.schema.validator.FieldValidator;
import global.maplink.validations.ValidationViolation;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;

import static global.maplink.planning.schema.problem.CompartmentType.FIXED;
import static java.util.Objects.isNull;
import static lombok.AccessLevel.PRIVATE;

@Data
@Builder
@AllArgsConstructor(access = PRIVATE)
@NoArgsConstructor(force = true)
public class Compartment {

    private final String name;
    @Builder.Default
    private final CompartmentType type = FIXED;
    private final Double minimumCapacity;
    private final Double maximumCapacity;
    private final Double increment;
    private final CompartmentLoadingRule loadingRule;
    private final Set<String> allowedPackagings;

    public List<ValidationViolation> validate(List<ValidationViolation> violations, Set<String> namesUsed) {

        if(isNull(name)){
            violations.add(PlanningUpdateViolation.of("compartment.name"));
        }

        if(FieldValidator.isNullOrNegative(minimumCapacity)){
            violations.add(PlanningUpdateViolation.of("compartment.minimumCapacity"));
        }

        if(FieldValidator.isNullOrNegative(maximumCapacity)){
            violations.add(PlanningUpdateViolation.of("compartment.maximumCapacity"));
        }

        if(FieldValidator.isNullOrNegative(increment)){
            violations.add(PlanningUpdateViolation.of("compartment.increment"));
        }

        if(isNull(loadingRule)){
            violations.add(PlanningUpdateViolation.of("compartment.loadingRule"));
        }

        if(isNull(allowedPackagings)){
            violations.add(PlanningUpdateViolation.of("compartment.allowedPackagings"));
        }

        checkNameUnique(violations, name, namesUsed);

        validateVariableCompartment(violations, this);

        return violations;
    }

    private void checkNameUnique(List<ValidationViolation> violations, String name, Set<String> namesUsed) {

        if (isNull(name) || isNull(namesUsed)) {
            return;
        }

        if (namesUsed.contains(name)) {
            violations.add(PlanningUpdateViolation.of("compartment.checkNameUnique"));
        }
        namesUsed.add(name);
    }

    private void validateVariableCompartment(List<ValidationViolation> violations, Compartment compartment) {

        if (compartment.getType() == null) {
            return;
        }

        if (!compartment.getType().equals(CompartmentType.VARIABLE)) {
            return;
        }

        if (FieldValidator.isNullOrNegative(compartment.minimumCapacity)) {
            violations.add(PlanningUpdateViolation.of("compartment.minimumCapacity"));
        }

        if (FieldValidator.isNullOrNegative(compartment.increment)) {
            violations.add(PlanningUpdateViolation.of("compartment.increment"));
        }
    }
}
