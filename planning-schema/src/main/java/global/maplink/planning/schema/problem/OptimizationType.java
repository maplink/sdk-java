package global.maplink.planning.schema.problem;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PRIVATE;

@Data
@Builder
@AllArgsConstructor(access = PRIVATE)
@NoArgsConstructor(force = true)

public class OptimizationType {

    public static final String CUSTOM = "CUSTOM";

    public static final String FIXED_SOLUTION = "FIXED_SOLUTION";
}
