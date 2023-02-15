package global.maplink.domain;

import global.maplink.MapLinkSDK;
import lombok.SneakyThrows;

import java.util.Objects;
import java.util.concurrent.Callable;
import java.util.function.Supplier;
import java.util.stream.Stream;

public enum PointsMode {
    OBJECT,
    ARRAY,
    GEOHASH,
    POLYLINE,
    SIMPLE;

    private static final ThreadLocal<PointsMode> threadCurrent = new ThreadLocal<>();

    private static Supplier<PointsMode> externalSupplier = () -> null;

    public static PointsMode current() {
        return Stream.of(
                        threadCurrent::get,
                        externalSupplier,
                        fromSDK()
                ).map(Supplier::get)
                .filter(Objects::nonNull)
                .findAny()
                .orElseGet(PointsMode::loadDefault);
    }

    private static Supplier<PointsMode> fromSDK() {
        return () -> MapLinkSDK.isInitialized() ? MapLinkSDK.getInstance().getPointsMode() : null;
    }

    @SneakyThrows
    public static <T> T runWith(PointsMode mode, Callable<T> action) {
        try {
            threadCurrent.set(mode);
            return action.call();
        } finally {
            threadCurrent.set(null);
        }
    }

    public static void setExternalSupplier(Supplier<PointsMode> supplier) {
        externalSupplier = supplier;
    }

    public static PointsMode loadDefault() {
        return OBJECT;
    }
}
