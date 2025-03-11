package global.maplink.geocode.schema.v1.suggestions;

import global.maplink.env.Environment;
import global.maplink.geocode.schema.v1.Type;
import global.maplink.geocode.schema.v2.suggestions.SuggestionsBaseRequest;
import global.maplink.http.request.Request;
import global.maplink.json.JsonMapper;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;
import lombok.val;

import java.util.Optional;

import static global.maplink.http.request.Request.get;

@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class SuggestionsRequest extends SuggestionsBaseRequest {

    public static final String PATH = "geocode/v1/suggestions";
    private static final String PARAM_LAST_MILE = "lastMile";


    private boolean lastMile;

    @Override
    public Request asHttpRequest(Environment environment, JsonMapper mapper) {
        val httpRequest = get(environment.withService(PATH))
                .withQuery(PARAM_QUERY, super.getQuery());

        Optional.ofNullable(super.getType())
                .map(Type::name)
                .ifPresent(v -> httpRequest.withQuery(PARAM_TYPE, v));

        Optional.of(lastMile)
                .ifPresent(v -> httpRequest.withQuery(PARAM_LAST_MILE, Boolean.toString(v)));

        return httpRequest;
    }
}
