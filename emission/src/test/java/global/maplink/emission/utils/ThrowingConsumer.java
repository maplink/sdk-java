package global.maplink.emission.utils;

public interface ThrowingConsumer<T> {
    void accept(T value) throws Throwable;
}