package global.maplink.geocode.schema;

import global.maplink.MapLinkServiceRequest;
import global.maplink.geocode.schema.suggestions.SuggestionsResult;
import global.maplink.http.Response;
import global.maplink.json.JsonMapper;

import java.util.function.Function;

public interface GeocodeServiceRequest extends MapLinkServiceRequest<SuggestionsResult> {

    @Override
    default Function<Response, SuggestionsResult> getResponseParser(JsonMapper mapper) {
        return r -> r.parseBodyObject(mapper, SuggestionsResult.class);
    }

}
