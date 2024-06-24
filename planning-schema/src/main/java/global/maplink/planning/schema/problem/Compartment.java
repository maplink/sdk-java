package global.maplink.planning.schema.problem;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import static global.maplink.planning.schema.problem.CompartmentType.FIXED;
import static lombok.AccessLevel.PRIVATE;

@Builder
@Data
@AllArgsConstructor(access = PRIVATE)
@NoArgsConstructor(force = true)
public class Compartment {

    private final String name;
    @Builder.Default
    private final String type = FIXED.toString();
    private final Double minimumCapacity;
    private final Double maximumCapacity;
    private final Double increment;
    private final String loadingRule;
    private final String[] allowedPackagings;

}
