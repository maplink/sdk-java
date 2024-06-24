package global.maplink.planning.schema.problem;

import lombok.*;

import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor(force = true)
@Builder
public class SolutionToRebuild {

    @Singular
    private final List<VehicleRoute> vehicleRoutes;

    private final String optimizationType = OptimizationType.CUSTOM;
}
