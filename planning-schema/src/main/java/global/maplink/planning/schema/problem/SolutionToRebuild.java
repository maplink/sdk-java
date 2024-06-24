package global.maplink.planning.schema.problem;

import lombok.*;

import java.util.List;

import static global.maplink.planning.schema.problem.OptimizationType.CUSTOM;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor(force = true)
public class SolutionToRebuild {

    @Singular
    private final List<VehicleRoute> vehicleRoutes;
    private final String optimizationType = CUSTOM.toString();
}
