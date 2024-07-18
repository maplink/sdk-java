package global.maplink.planning.schema.validator;

import global.maplink.planning.schema.problem.TimeWindow;
import global.maplink.planning.schema.problem.VehicleRoute;

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
}
