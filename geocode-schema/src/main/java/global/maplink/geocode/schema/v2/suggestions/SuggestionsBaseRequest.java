package global.maplink.geocode.schema.v2.suggestions;

import global.maplink.env.Environment;
import global.maplink.geocode.schema.GeocodeServiceRequest;
import global.maplink.geocode.schema.v2.Type;
import global.maplink.http.request.Request;
import global.maplink.json.JsonMapper;
import lombok.Data;
import lombok.experimental.SuperBuilder;
import lombok.val;

import java.util.Optional;

import static global.maplink.http.request.Request.get;

@SuperBuilder
@Data
public class SuggestionsBaseRequest implements GeocodeServiceRequest {

    public static final String PATH = "geocode/v2/suggestions";
    protected static final String PARAM_QUERY = "q";
    protected static final String PARAM_TYPE = "type";

    private final String query;

    private Type type;

    @Override
    public Request asHttpRequest(Environment environment, JsonMapper mapper) {
        val httpRequest = get(environment.withService(PATH))
                .withQuery(PARAM_QUERY, query);

        Optional.ofNullable(type)
                .map(Type::name)
                .ifPresent(v -> httpRequest.withQuery(PARAM_TYPE, v));

        return httpRequest;
    }
}
