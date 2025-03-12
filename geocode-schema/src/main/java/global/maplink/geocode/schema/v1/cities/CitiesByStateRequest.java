package global.maplink.geocode.schema.v1.cities;

import global.maplink.env.Environment;
import global.maplink.geocode.schema.GeocodeServiceRequest;
import global.maplink.http.request.Request;
import global.maplink.http.request.RequestBody;
import global.maplink.json.JsonMapper;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import static global.maplink.http.request.Request.post;

@RequiredArgsConstructor
@Builder
@Data
public class CitiesByStateRequest implements GeocodeServiceRequest {

    public static final String PATH = "/geocode/states/%s/cities";
    public final String state;

    @Override
    public Request asHttpRequest(Environment environment, JsonMapper mapper) {
        return post(
                environment.withService(String.format(PATH, state)),
                RequestBody.Json.of(this, mapper)
        );
    }
}
