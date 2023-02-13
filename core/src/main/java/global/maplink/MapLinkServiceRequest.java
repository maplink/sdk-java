package global.maplink;

import global.maplink.env.Environment;
import global.maplink.http.Response;
import global.maplink.http.request.Request;
import global.maplink.json.JsonMapper;
import global.maplink.validations.Validable;
import global.maplink.validations.ValidationViolation;

import java.util.List;
import java.util.function.Function;

import static java.util.Collections.emptyList;

public interface MapLinkServiceRequest<T> extends Validable {

    Request asHttpRequest(Environment environment, JsonMapper mapper);

    Function<Response, T> getResponseParser(JsonMapper mapper);

    @Override
    default List<ValidationViolation> validate() {
        return emptyList();
    }
}
