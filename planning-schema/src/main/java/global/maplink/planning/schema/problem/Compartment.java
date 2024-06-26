package global.maplink.planning.schema.problem;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

import static global.maplink.planning.schema.problem.CompartmentType.FIXED;
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
    private final String loadingRule;
    private final List<String> allowedPackagings;

}
