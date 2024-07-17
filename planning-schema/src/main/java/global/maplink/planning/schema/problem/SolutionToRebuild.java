package global.maplink.planning.schema.problem;

import global.maplink.planning.schema.exception.PlanningUpdateViolation;
import global.maplink.planning.schema.validator.FieldValidator;
import global.maplink.validations.ValidationViolation;
import lombok.*;

import java.util.LinkedList;
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

    public List<ValidationViolation> validate() {
        List<ValidationViolation> violations = new LinkedList<>();

        if(FieldValidator.isInvalid(vehicleRoutes)){
            violations.add(PlanningUpdateViolation.of("solutionToRebuild.vehicleRoutes"));
        }

        return violations;
    }
}
