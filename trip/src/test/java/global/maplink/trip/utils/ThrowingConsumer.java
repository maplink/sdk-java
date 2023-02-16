package global.maplink.trip.utils;

public interface ThrowingConsumer<T> {
    void accept(T value) throws Throwable;
}