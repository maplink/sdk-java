package global.maplink.planning.schema.problem;

import global.maplink.planning.schema.exception.PlanningUpdateViolation;
import global.maplink.validations.Validable;
import global.maplink.validations.ValidationViolation;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

import static global.maplink.planning.schema.exception.PlanningErrorType.START_EQUALS_END;
import static global.maplink.planning.schema.exception.PlanningErrorType.START_IS_BIGGER_THAN_END;
import static lombok.AccessLevel.PRIVATE;

@Data
@Builder
@AllArgsConstructor(access = PRIVATE)
@NoArgsConstructor(force = true)
public class TimeWindow implements Validable {

    private final Long start;
    private final Long end;

    public List<ValidationViolation> validate() {
        List<ValidationViolation> violations = new LinkedList<>();

        if(Objects.isNull(start)){
            violations.add(PlanningUpdateViolation.of("timeWindow.start"));
        }

        if(Objects.isNull(end)){
            violations.add(PlanningUpdateViolation.of("timeWindow.end"));
        }

        startLessThanOrEqualToEnd(violations);

        return violations;
    }

    private void startLessThanOrEqualToEnd(List<ValidationViolation> violations){
        if (start == null || end == null) {
            return;
        }

        if(start > end){
            violations.add(PlanningUpdateViolation.of(START_EQUALS_END.getMessage()));
        }

        if(start.equals(end)){
            violations.add(PlanningUpdateViolation.of(START_IS_BIGGER_THAN_END.getMessage()));
        }
    }
}
