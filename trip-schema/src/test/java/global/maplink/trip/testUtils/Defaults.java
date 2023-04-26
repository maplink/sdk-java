package global.maplink.trip.testUtils;

import org.assertj.core.data.Offset;

import static org.assertj.core.data.Offset.offset;

public class Defaults {
    public static final Offset<Double> POINT_OFFSET = offset(10e-5);
}
