package global.maplink.http;

import global.maplink.http.exceptions.HttpException;
import global.maplink.json.JsonMapper;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class Response {

    private final int statusCode;

    private final String contentType;

    private final byte[] body;

    public <T> T parseBodyObject(JsonMapper mapper, Class<T> type) {
        return mapper.fromJson(body, type);
    }

    public <T> List<T> parseBodyArray(JsonMapper mapper, Class<T> type) {
        return mapper.fromJsonList(body, type);
    }

    public boolean isOk() {
        return statusCode >= 200 && statusCode < 300;
    }

    public boolean isError() {
        return !isOk();
    }

    public boolean isClientError() {
        return statusCode >= 400 && statusCode < 500;
    }

    public boolean isServerError() {
        return statusCode >= 500 && statusCode < 600;
    }

    public void throwIfIsError() {
        if (isError()) {
            throw new HttpException(statusCode, contentType, body);
        }
    }
}
