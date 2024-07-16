package global.maplink.planning.schema.problem;

import global.maplink.place.schema.exception.PlaceUpdateViolation;
import global.maplink.validations.Validable;
import global.maplink.validations.ValidationViolation;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import static global.maplink.planning.schema.problem.CompartmentType.FIXED;
import static java.util.Objects.isNull;
import static lombok.AccessLevel.PRIVATE;

@Data
@Builder
@AllArgsConstructor(access = PRIVATE)
@NoArgsConstructor(force = true)
public class Compartment implements Validable {

    private final String name;
    @Builder.Default
    private final CompartmentType type = FIXED;
    private final Double minimumCapacity;
    private final Double maximumCapacity;
    private final Double increment;
    private final CompartmentLoadingRule loadingRule;
    private final Set<String> allowedPackagings;

    public List<ValidationViolation> validate() {
        List<ValidationViolation> violations = new LinkedList<>();
        if (isNull(loadingRule)) {
            violations.add(PlaceUpdateViolation.of("compartment.loadingRule"));
        }

        return violations;
    }
}
