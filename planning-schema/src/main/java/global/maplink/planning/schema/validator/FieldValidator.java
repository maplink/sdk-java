package global.maplink.planning.schema.validator;

import global.maplink.planning.schema.exception.PlanningUpdateViolation;
import global.maplink.planning.schema.problem.TimeWindow;
import global.maplink.planning.schema.problem.VehicleRoute;
import global.maplink.validations.ValidationViolation;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static java.util.Objects.isNull;

public class FieldValidator {

    private FieldValidator() {
        throw new IllegalStateException("Utility class");
    }

    public static boolean isInvalid(final String value) {
        return isNull(value) || value.trim().isEmpty();
    }

    public static boolean isInvalid(final List<VehicleRoute> value) {
        return isNull(value) || value.isEmpty();
    }

    public static boolean isNotNegative(final Double value) {
        return value >= 0.0;
    }

    public static boolean isNotNegative(final Integer value) {
        return value >= 0.0;
    }

    public static boolean isGreaterThanZero(final Integer value) {
        return value > 0;
    }

    public static boolean isEmpty(final List<TimeWindow> value) {
        return value.isEmpty();
    }

    public static boolean isNullOrNegative(final Double value) {
        return isNull(value) || value < 0;
    }

    public final <T> void unique(List<ValidationViolation> violations, T value, Collection<T> allValues) {
        if (value == null)
            return;

        int count = 0;
        for (T temp : allValues) {
            if (value.equals(temp))
                count++;

            if (count > 1) {
                violations.add(PlanningUpdateViolation.of("fieldValidator.unique"));
                return;
            }
        }
    }

    public final <T> void isContainedIn(List<ValidationViolation> violations, T value, T... allValues){
        if (value != null && !Arrays.asList(allValues).contains(value)) {
            violations.add(PlanningUpdateViolation.of("fieldValidator.isContainedIn"));
        }
    }

    public final void validReference(List<ValidationViolation> violations, String value, Collection<String> allValues) {
        if (value != null && !allValues.contains(value)) {
            violations.add(PlanningUpdateViolation.of("fieldValidator.validReference"));
        }
    }
}
