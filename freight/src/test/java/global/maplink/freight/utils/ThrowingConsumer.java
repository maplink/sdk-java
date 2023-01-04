package global.maplink.freight.utils;

public interface ThrowingConsumer<T> {
    void accept(T value) throws Throwable;
}