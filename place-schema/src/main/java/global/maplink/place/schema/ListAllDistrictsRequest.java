package global.maplink.place.schema;

import global.maplink.MapLinkServiceRequest;
import global.maplink.env.Environment;
import global.maplink.http.Response;
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
public class ListAllDistrictsRequest implements MapLinkServiceRequest<PlacePageResult> {

    private final String state;
    private final String city;
    public final String path = "place/v1/places/state/" + state + "/city/" + city + "/districts";

    @Override
    public Request asHttpRequest(Environment environment, JsonMapper mapper) {
        return get(environment.withService(path));
    }

    @Override
    public Function<Response, PlacePageResult> getResponseParser(JsonMapper mapper) {
        return r -> r.parseBodyObject(mapper, PlacePageResult.class);
    }
}
