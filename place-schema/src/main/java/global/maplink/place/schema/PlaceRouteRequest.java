package global.maplink.place.schema;

import global.maplink.MapLinkServiceRequest;
import global.maplink.env.Environment;
import global.maplink.http.Response;
import global.maplink.http.request.Request;
import global.maplink.http.request.RequestBody;
import global.maplink.json.JsonMapper;
import global.maplink.place.schema.exception.PlaceCalculationRequestException;
import global.maplink.place.schema.exception.PlaceErrorType;
import global.maplink.validations.Validable;
import global.maplink.validations.ValidationViolation;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.function.Function;

import static global.maplink.http.request.Request.post;
import static lombok.AccessLevel.PRIVATE;

@Data
@Builder
@RequiredArgsConstructor(staticName = "of")
@NoArgsConstructor(force = true, access = PRIVATE)
public class PlaceRouteRequest implements MapLinkServiceRequest<PlaceRouteResponse>, Validable {

    public static final String PATH = "place/v1/calculations";

    public static final int MAX_BUFFER = 500;
    @Singular
    private final Set<Category> categories;
    @Singular
    private final Set<SubCategory> subCategories;
    private final Long bufferRouteInMeters;
    private final Long bufferStoppingPointsInMeters;
    private final boolean onlyMyPlaces;
    @Singular
    private final List<Leg> legs;

    @Override
    public List<ValidationViolation> validate() {
        List<ValidationViolation> errors = new ArrayList<>();

        if (getBufferRouteInMeters() <= 0) {
            errors.add(PlaceErrorType.ROUTE_BUFFER_LESS_THAN_ZERO);
        }

        if (getBufferStoppingPointsInMeters() <= 0) {
            errors.add(PlaceErrorType.STOPPING_POINTS_LESS_THAN_ZERO);
        }

        if (!containsCategory() && !containsSubCategory()) {
            errors.add(PlaceErrorType.CATEGORY_SUBCATEGORY_NECESSARY);
        }

        if (getBufferRouteInMeters() > MAX_BUFFER) {
            errors.add(PlaceErrorType.ROUTE_BUFFER_BIGGER_THAN_MAX_BUFFER);
        }

        if (getBufferStoppingPointsInMeters() > MAX_BUFFER) {
            errors.add(PlaceErrorType.STOPPING_POINTS_BIGGER_THAN_MAX_BUFFER);
        }

        return errors;
    }

    public void validateWithLegs() {
        throwIfInvalid();

        if (getLegs() == null || getLegs().isEmpty()) {
            throw new PlaceCalculationRequestException(PlaceErrorType.LEGS_INFO_NEEDED);
        }
    }

    private boolean containsCategory() {
        return categories != null && !categories.isEmpty();
    }

    private boolean containsSubCategory() {
        return subCategories != null && !subCategories.isEmpty();
    }

    @Override
    public Request asHttpRequest(Environment environment, JsonMapper mapper) {
        return post(
                environment.withService(PATH),
                RequestBody.Json.of(this, mapper)
        );
    }

    @Override
    public Function<Response, PlaceRouteResponse> getResponseParser(JsonMapper mapper) {
        return response -> response.parseBodyObject(mapper, PlaceRouteResponse.class);
    }
}
