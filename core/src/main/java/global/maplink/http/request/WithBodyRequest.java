package global.maplink.http.request;

import lombok.Getter;

import java.net.URL;

public class WithBodyRequest extends Request {

    @Getter
    private final byte[] body;

    public WithBodyRequest(URL url, byte[] body) {
        super(url);
        this.body = body;
    }

    public WithBodyRequest(URL url, RequestBody body) {
        this(url, body.getBytes());
        this.withHeader("Content-Type", body.getContentType());
    }
}
