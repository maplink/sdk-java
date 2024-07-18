package global.maplink.planning.schema.problem;

import global.maplink.planning.schema.exception.PlanningUpdateViolation;
import global.maplink.validations.ValidationViolation;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

import static java.util.Objects.isNull;
import static lombok.AccessLevel.PRIVATE;

@Data
@Builder
@AllArgsConstructor(access = PRIVATE)
@NoArgsConstructor(force = true)
public class Coordinates {

    private final Double longitude;
    private final Double latitude;

    public List<ValidationViolation> validate(List<ValidationViolation> violations, Coordinates coordinates) {

        if(isNull(longitude) || longitude > 180 || longitude < -180){
            violations.add(PlanningUpdateViolation.of("coordinates.longitude"));
        }

        if(isNull(latitude) || latitude > 90 || latitude < -90){
            violations.add(PlanningUpdateViolation.of("coordinates.latitude"));
        }

        return violations;
    }
}
