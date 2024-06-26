package global.maplink.planning.schema.problem;

import lombok.*;

import java.util.List;

import static global.maplink.planning.schema.problem.OptimizationType.CUSTOM;
import static lombok.AccessLevel.PRIVATE;

@Data
@Builder
@AllArgsConstructor(access = PRIVATE)
@NoArgsConstructor(force = true)
public class SolutionToRebuild {

    @Singular
    private final List<VehicleRoute> vehicleRoutes;
    @Builder.Default
    private final OptimizationType optimizationType = CUSTOM;
}
