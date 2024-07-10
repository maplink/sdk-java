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

import static java.util.Objects.isNull;
import static lombok.AccessLevel.PRIVATE;

@Data
@Builder
@AllArgsConstructor(access = PRIVATE)
@NoArgsConstructor(force = true)
public class Product implements Validable {

    private final String name;
    private final String type;
    private final String incompabilityGroup;
    private final Set<String> packagings;

    public List<ValidationViolation> validate() {
        List<ValidationViolation> violations = new LinkedList<>();
        if (isInvalid(name)) {
            violations.add(PlaceUpdateViolation.of("product.name"));
        }

        if (isInvalid(type)) {
            violations.add(PlaceUpdateViolation.of("product.type"));
        }

        if (isInvalid(incompabilityGroup)) {
            violations.add(PlaceUpdateViolation.of("product.incompabilityGroup"));
        }

        if (isNull(packagings)) {
            violations.add(PlaceUpdateViolation.of("product.packagings"));
        }

        return violations;
    }

    private boolean isInvalid(final String value) {
        return isNull(value) || value.trim().isEmpty();
    }
}
