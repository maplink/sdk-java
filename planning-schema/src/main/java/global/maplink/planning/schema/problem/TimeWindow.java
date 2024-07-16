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

import static java.util.Objects.isNull;
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
        if (isInvalid(String.valueOf(start))) {
            violations.add(PlaceUpdateViolation.of("timeWindow.start"));
        }

        if (isInvalid(String.valueOf(end))) {
            violations.add(PlaceUpdateViolation.of("timeWindow.end"));
        }

        return violations;
    }

    private boolean isInvalid(final String value) {
        return isNull(value) || value.trim().isEmpty();
    }
}
