package global.maplink.json;

import global.maplink.NoImplementationFoundException;

import java.util.List;
import java.util.ServiceLoader;

import static java.nio.charset.StandardCharsets.UTF_8;

public interface JsonMapper {


    <T> T fromJson(byte[] data, Class<T> type);

    default <T> T fromJson(String data, Class<T> type) {
        return fromJson(data.getBytes(), type);
    }

    <T> List<T> fromJsonList(byte[] data, Class<T> type);

    default <T> List<T> fromJsonList(String data, Class<T> type) {
        return fromJsonList(data.getBytes(), type);
    }

    <T> byte[] toJson(T object);

    default <T> String toJsonString(T object) {
        return new String(toJson(object), UTF_8);
    }


    static JsonMapper loadDefault() {
        return ServiceLoader.load(JsonMapper.class)
                .findFirst()
                .orElseThrow(() -> new NoImplementationFoundException(JsonMapper.class));
    }
}
