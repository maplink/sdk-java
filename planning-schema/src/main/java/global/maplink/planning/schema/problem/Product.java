package global.maplink.planning.schema.problem;

import global.maplink.planning.schema.exception.PlanningUpdateViolation;
import global.maplink.planning.schema.validator.FieldValidator;
import global.maplink.validations.ValidationViolation;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import static lombok.AccessLevel.PRIVATE;

@Data
@Builder
@AllArgsConstructor(access = PRIVATE)
@NoArgsConstructor(force = true)
public class Product {

    private final String name;
    private final String type;
    private final String incompabilityGroup;
    private final Set<String> packagings;

    public List<ValidationViolation> validate() {
        List<ValidationViolation> violations = new LinkedList<>();

        if(FieldValidator.isInvalid(name)){
            violations.add(PlanningUpdateViolation.of("product.name"));
        }

        return violations;
    }
}
