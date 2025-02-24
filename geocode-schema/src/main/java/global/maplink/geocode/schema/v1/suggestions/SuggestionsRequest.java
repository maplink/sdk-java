package global.maplink.geocode.schema.v1.suggestions;

import global.maplink.env.Environment;
import global.maplink.geocode.schema.v1.GeocodeServiceRequest;
import global.maplink.geocode.schema.v1.Type;
import global.maplink.http.request.Request;
import global.maplink.json.JsonMapper;
import lombok.Builder;
import lombok.Data;
import lombok.val;

import java.util.Optional;

import static global.maplink.http.request.Request.get;

@Builder
@Data
public class SuggestionsRequest implements GeocodeServiceRequest {

    public static final String PATH = "geocode/v1/suggestions";
    private static final String PARAM_QUERY = "q";
    private static final String PARAM_TYPE = "type";
    private static final String PARAM_LAST_MILE = "lastMile";

    private final String query;

    private Type type;

    private boolean lastMile;

    @Override
    public Request asHttpRequest(Environment environment, JsonMapper mapper) {
        val httpRequest = get(environment.withService(PATH))
                .withQuery(PARAM_QUERY, query);

        Optional.ofNullable(type)
                .map(Type::name)
                .ifPresent(v -> httpRequest.withQuery(PARAM_TYPE, v));

        Optional.of(lastMile)
                .ifPresent(v -> httpRequest.withQuery(PARAM_LAST_MILE, Boolean.toString(v)));

        return httpRequest;
    }
}
