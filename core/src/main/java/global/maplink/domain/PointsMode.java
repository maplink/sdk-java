package global.maplink.domain;

import global.maplink.MapLinkSDK;

import java.util.Optional;
import java.util.concurrent.Callable;
import java.util.function.Supplier;

public enum PointsMode {
    OBJECT,
    ARRAY,
    GEOCODE,
    POLYLINE;

    private static final ThreadLocal<PointsMode> current = new ThreadLocal<>();

    public static PointsMode current() {
        MapLinkSDK sdk = MapLinkSDK.getInstance();
        return Optional.ofNullable(current.get()).orElseGet(sdk::getPointsMode);
    }

    public static <T> T runWith(PointsMode mode, Callable<T> action) throws Exception {
        try {
            current.set(mode);
            return action.call();
        } finally {
            current.set(null);
        }
    }

    public static <T> T runWith(PointsMode mode, Supplier<T> action) {
        try {
            current.set(mode);
            return action.get();
        } finally {
            current.set(null);
        }
    }

    public static PointsMode loadDefault() {
        return OBJECT;
    }
}
