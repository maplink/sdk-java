package global.maplink.http.exceptions;

import global.maplink.http.MediaType.Application;
import global.maplink.http.MediaType.Text;
import lombok.Getter;

import static java.lang.String.format;
import static java.util.Optional.ofNullable;

@Getter
public class HttpException extends RuntimeException {
    private final int status;
    private final String contentType;
    private final byte[] body;


    public HttpException(int status, String contentType, byte[] body) {
        super(toMessage(status, contentType, body));
        this.status = status;
        this.contentType = contentType;
        this.body = body;
    }

    private static String toMessage(int status, String contentType, byte[] body) {
        String parsedContentType = ofNullable(contentType)
                .map(s -> s.split(";")[0])
                .map(String::trim)
                .orElse("undefined");
        String message;
        if (Text.PLAIN.equalsIgnoreCase(parsedContentType) || Application.JSON.equalsIgnoreCase(parsedContentType)) {
            message = new String(body);
        } else {
            message = format("%d bytes", body.length);
        }
        return format("HTTP Error %s with body %s of type %s", status, message, contentType);
    }


}
