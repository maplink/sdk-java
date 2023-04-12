package global.maplink.place.schema;

import global.maplink.MapLinkServiceRequest;
import global.maplink.env.Environment;
import global.maplink.http.Response;
import global.maplink.http.request.GetRequest;
import global.maplink.http.request.Request;
import global.maplink.json.JsonMapper;
import lombok.*;

import java.util.function.Function;

import static global.maplink.http.request.Request.get;

@EqualsAndHashCode
@ToString
@Builder
@RequiredArgsConstructor
@NoArgsConstructor(force = true)
public class ListAllStatesRequest implements MapLinkServiceRequest<PlacePageResult> {
    public static final String PATH = "place/v1/places/states";

    @Override
    public Request asHttpRequest(Environment environment, JsonMapper mapper) {
        return get(environment.withService(PATH));
    }

    @Override
    public Function<Response, PlacePageResult> getResponseParser(JsonMapper mapper) {
        return r -> r.parseBodyObject(mapper, PlacePageResult.class);
    }
}
