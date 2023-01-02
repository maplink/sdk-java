package global.maplink.http.request;

import java.net.URL;

public class PostRequest extends WithBodyRequest {

    public PostRequest(URL url, byte[] body) {
        super(url, body);
    }

    public PostRequest(URL url, RequestBody body) {
        super(url, body);
    }

    @Override
    public PostRequest withQuery(String key, String value) {
        return (PostRequest) super.withQuery(key, value);
    }

    @Override
    public PostRequest withHeader(String key, String value) {
        return (PostRequest) super.withHeader(key, value);
    }

    @Override
    public PostRequest withAuthorizationHeader(String value) {
        return (PostRequest) super.withAuthorizationHeader(value);
    }

}