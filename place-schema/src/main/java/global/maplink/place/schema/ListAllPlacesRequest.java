package global.maplink.place.schema;

import global.maplink.MapLinkServiceRequest;
import global.maplink.domain.MaplinkPoint;
import global.maplink.env.Environment;
import global.maplink.http.Response;
import global.maplink.http.request.GetRequest;
import global.maplink.http.request.Request;
import global.maplink.json.JsonMapper;
import lombok.*;

import java.util.List;
import java.util.function.Function;

import static global.maplink.http.request.Request.get;

@EqualsAndHashCode
@ToString
@Builder
@RequiredArgsConstructor
@NoArgsConstructor(force = true)
public class ListAllPlacesRequest implements MapLinkServiceRequest<PlacePageResult> {

    public static final String PATH = "place/v1/places";

    private final Integer offset;
    private final Integer limit;
    private final String state;
    private final String city;
    private final String district;
    @Singular
    private final List<String> tags;
    private final MaplinkPoint center;
    private final Integer radius;

    @Override
    public Request asHttpRequest(Environment environment, JsonMapper mapper) {
        GetRequest request = get(
                environment.withService(PATH)
        );
        if (offset != null && offset > 0) {
            request = request.withQuery("offset", offset.toString());
        }
        if (limit != null && limit > 0) {
            request = request.withQuery("offset", limit.toString());
        }
        if (state != null && !state.isEmpty()) {
            request = request.withQuery("state", state);
        }
        if (city != null && !city.isEmpty()) {
            request = request.withQuery("city", city);
        }
        if (district != null && !district.isEmpty()) {
            request = request.withQuery("district", district);
        }
        if (tags != null && !tags.isEmpty()) {
            request = request.withQuery("tags", tags.toString());
        }
        if ((center != null) && (center.getLatitude() > 0) && center.getLongitude() > 0) {
            request = request.withQuery("center", center.toString());
        }
        if (radius != null && radius > 0) {
            request = request.withQuery("radius", radius.toString());
        }

        return request;
    }

    @Override
    public Function<Response, PlacePageResult> getResponseParser(JsonMapper mapper) {
        return r -> r.parseBodyObject(mapper, PlacePageResult.class);
    }
}
