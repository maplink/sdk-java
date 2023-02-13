package global.maplink.place.schema;

import global.maplink.MapLinkServiceRequest;
import global.maplink.env.Environment;
import global.maplink.http.Response;
import global.maplink.http.request.Request;
import global.maplink.http.request.RequestBody;
import global.maplink.json.JsonMapper;
import global.maplink.validations.Validable;
import global.maplink.validations.ValidationViolation;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.util.LinkedList;
import java.util.List;
import java.util.function.Function;

import static global.maplink.http.request.Request.post;
import static global.maplink.place.schema.exception.PlaceErrorType.REQUIRED_FIELDS_INVALID;
import static java.util.Objects.isNull;

@EqualsAndHashCode
@ToString
@RequiredArgsConstructor
public class CreatePlaceRequest implements MapLinkServiceRequest<Void>, Validable {

    public static final String PATH = "place/v1/places";

    private final Place place;

    @Override
    public Request asHttpRequest(Environment environment, JsonMapper mapper) {
        return post(
                environment.withService(PATH),
                RequestBody.Json.of(place, mapper)
        );
    }

    @Override
    public Function<Response, Void> getResponseParser(JsonMapper mapper) {
        return response -> null;
    }

    @Override
    public List<ValidationViolation> validate() {
        List<ValidationViolation> violations = new LinkedList<>();
        if (isNull(place)) {
            violations.add(REQUIRED_FIELDS_INVALID);
        } else {
            violations.addAll(place.validate());
        }
        return violations;
    }
}
