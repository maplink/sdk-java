package global.maplink.http.request;

import java.net.URL;

public class GetRequest extends Request {
    public GetRequest(URL url) {
        super(url);
    }

    @Override
    public GetRequest withQuery(String key, String value) {
        return (GetRequest) super.withQuery(key, value);
    }

    @Override
    public GetRequest withHeader(String key, String value) {
        return (GetRequest) super.withHeader(key, value);
    }

    @Override
    public GetRequest withAuthorizationHeader(String value) {
        return (GetRequest) super.withAuthorizationHeader(value);
    }
}