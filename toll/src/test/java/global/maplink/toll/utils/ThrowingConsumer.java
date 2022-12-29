package global.maplink.toll.utils;

public interface ThrowingConsumer<T> {
    void accept(T value) throws Throwable;
}