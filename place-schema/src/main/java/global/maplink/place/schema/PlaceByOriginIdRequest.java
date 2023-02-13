package global.maplink.place.schema;

import global.maplink.MapLinkServiceRequest;
import global.maplink.env.Environment;
import global.maplink.http.Response;
import global.maplink.http.request.Request;
import global.maplink.json.JsonMapper;
import global.maplink.validations.Validable;
import global.maplink.validations.ValidationViolation;
import lombok.*;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import static global.maplink.http.request.Request.get;
import static global.maplink.place.schema.exception.PlaceErrorType.REQUIRED_FIELDS_INVALID;
import static java.lang.String.format;
import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;
import static java.util.Optional.empty;

@EqualsAndHashCode
@ToString
@Builder
@RequiredArgsConstructor
@NoArgsConstructor(force = true)
public class PlaceByOriginIdRequest implements MapLinkServiceRequest<Optional<Place>>, Validable {

    public static final String PATH = "place/v1/places/%s";
    private static final int NO_CONTENT = 204;

    private final String originId;

    @Override
    public Request asHttpRequest(Environment environment, JsonMapper mapper) {
        throwIfInvalid();
        return get(
                environment.withService(format(PATH, originId))
        );
    }

    public Function<Response, Optional<Place>> getResponseParser(JsonMapper mapper) {
        return (response) -> {
            if (response.getStatusCode() == NO_CONTENT) {
                return empty();
            }
            return Optional.of(response.parseBodyObject(mapper, Place.class));
        };
    }

    @Override
    public List<ValidationViolation> validate() {
        if (originId == null || originId.trim().isEmpty()) {
            return singletonList(REQUIRED_FIELDS_INVALID);
        }
        return emptyList();
    }
}
