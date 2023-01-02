package global.maplink.http.request;

import java.net.URL;

public class PutRequest extends WithBodyRequest {

    public PutRequest(URL url, byte[] body) {
        super(url, body);
    }

    public PutRequest(URL url, RequestBody body) {
        super(url, body);
    }

    @Override
    public PutRequest withQuery(String key, String value) {
        return (PutRequest) super.withQuery(key, value);
    }

    @Override
    public PutRequest withHeader(String key, String value) {
        return (PutRequest) super.withHeader(key, value);
    }

    @Override
    public PutRequest withAuthorizationHeader(String value) {
        return (PutRequest) super.withAuthorizationHeader(value);
    }

}