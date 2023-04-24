package global.maplink.place.schema;

import global.maplink.MapLinkServiceRequest;
import global.maplink.env.Environment;
import global.maplink.http.Response;
import global.maplink.http.request.Request;
import global.maplink.json.JsonMapper;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.List;
import java.util.function.Function;

import static global.maplink.http.request.Request.get;

@EqualsAndHashCode
@ToString
@Builder
public class ListAllStatesRequest implements MapLinkServiceRequest<List<String>> {
    public static final String PATH = "place/v1/places/states";

    @Override
    public Request asHttpRequest(Environment environment, JsonMapper mapper) {
        return get(environment.withService(PATH));
    }

    @Override
    public Function<Response, List<String>> getResponseParser(JsonMapper mapper) {
        return r -> r.parseBodyArray(mapper, String.class);
    }
}
