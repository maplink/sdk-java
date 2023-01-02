package global.maplink.place.utils;

public interface ThrowingConsumer<T> {
    void accept(T value) throws Throwable;
}