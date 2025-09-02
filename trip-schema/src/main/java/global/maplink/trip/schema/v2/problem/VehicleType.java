package global.maplink.trip.schema.v2.problem;

import java.util.Arrays;
import java.util.stream.Collectors;

public enum VehicleType {
    CAR,
    MOTORCYCLE,
    CAR_WITH_THREE_SIMPLE_AXLES,
    CAR_WITH_FOUR_SIMPLE_AXLES,
    BUS_WITH_TWO_DOUBLE_AXLES,
    BUS_WITH_THREE_DOUBLE_AXLES,
    BUS_WITH_FOUR_DOUBLE_AXLES,
    BUS_WITH_FIVE_DOUBLE_AXLES,
    TRUCK_WITH_TWO_SINGLE_AXIS,
    TRUCK_WITH_TWO_DOUBLE_AXLES,
    TRUCK_WITH_THREE_DOUBLE_AXLES,
    TRUCK_WITH_FOUR_DOUBLE_AXLES,
    TRUCK_WITH_FIVE_DOUBLE_AXLES,
    TRUCK_WITH_SIX_DOUBLE_AXLES,
    TRUCK_WITH_SEVEN_DOUBLE_AXLES,
    TRUCK_WITH_EIGHT_DOUBLE_AXLES,
    TRUCK_WITH_NINE_DOUBLE_AXLES,
    TRUCK_WITH_TEN_DOUBLE_AXLES;

    public static String getAllowedValues() {
        return Arrays.stream(VehicleType.values())
                .map(Enum::name)
                .collect(Collectors.joining(", "));
    }
}