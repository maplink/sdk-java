package global.maplink.planning.schema.problem;

import global.maplink.planning.schema.exception.PlanningUpdateViolation;
import global.maplink.planning.schema.validator.FieldValidator;
import global.maplink.validations.ValidationViolation;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.*;

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

        if(FieldValidator.isInvalid(name)){
            violations.add(PlanningUpdateViolation.of("compartmentConfiguration.name"));
        }

        //invocar validaçao do compartment
        return violations;
    }

    private void validateCompartments(List<String> errors, String fieldPath, Compartment[] compartments) {

        Set<String> namesUsed = new HashSet<>();
        for (int i = 0; i < compartments.length; i++) {
            //Compartment.isValid(errors, fieldPath + "[" + i + "].", compartments[i], namesUsed);
            this.compartments.get(i).validate(namesUsed);
        }

//        for (Compartment compartment : this.compartments) {
//            compartment.validate();
//        }


       // this.compartments.forEach(compartment -> compartment.validate());

        //this.compartments.forEach(Compartment::validate);
    }
}
