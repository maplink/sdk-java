package global.maplink;

import global.maplink.env.Environment;
import global.maplink.http.request.Request;
import global.maplink.json.JsonMapper;

public interface MapLinkServiceRequest {

    Request asHttpRequest(Environment environment, JsonMapper mapper);
}
