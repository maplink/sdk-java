package global.maplink.json;

public class JsonException extends RuntimeException {
    public JsonException(String message, Exception cause) {
        super(message, cause);
    }

    public JsonException(Exception cause) {
        super(cause);
    }
}
