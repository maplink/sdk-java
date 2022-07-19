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
}