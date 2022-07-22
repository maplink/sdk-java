package global.maplink.json;

import java.util.List;

public class MockJsonMapper implements JsonMapper {
    @Override
    public <T> T fromJson(byte[] data, Class<T> type) {
        return null;
    }

    @Override
    public <T> List<T> fromJsonList(byte[] data, Class<T> type) {
        return null;
    }

    @Override
    public <T> byte[] toJson(T object) {
        return new byte[0];
    }
}
