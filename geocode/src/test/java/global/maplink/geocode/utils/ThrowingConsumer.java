package global.maplink.geocode.utils;

public interface ThrowingConsumer<T> {
    void accept(T value) throws Throwable;
}