package global.maplink.place.schema;

import global.maplink.MapLinkServiceRequest;
import global.maplink.env.Environment;
import global.maplink.http.request.Request;
import global.maplink.http.request.RequestBody;
import global.maplink.json.JsonMapper;
import global.maplink.place.schema.exception.ErrorType;
import global.maplink.place.schema.exception.PlaceCalculationRequestException;
import lombok.*;

import java.util.List;
import java.util.Set;

import static global.maplink.http.request.Request.post;
import static lombok.AccessLevel.PRIVATE;

@Data
@Builder
@RequiredArgsConstructor(staticName = "of")
@NoArgsConstructor(force = true, access = PRIVATE)
public class PlaceRouteRequest implements MapLinkServiceRequest {

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

    public void validate() {
        if (getBufferRouteInMeters() <= 0) {
            throw new PlaceCalculationRequestException(ErrorType.PLACE_0001);
        }

        if (getBufferStoppingPointsInMeters() <= 0) {
            throw new PlaceCalculationRequestException(ErrorType.PLACE_0002);
        }

        if (!containsCategory() && !containsSubCategory()) {
            throw new PlaceCalculationRequestException(ErrorType.PLACE_0003);
        }

        if (getBufferRouteInMeters() > MAX_BUFFER) {
            throw new PlaceCalculationRequestException(ErrorType.PLACE_0005);
        }

        if (getBufferStoppingPointsInMeters() > MAX_BUFFER) {
            throw new PlaceCalculationRequestException(ErrorType.PLACE_0006);
        }
    }

    public void validateWithLegs() {
        validate();

        if (getLegs() == null || getLegs().isEmpty()) {
            throw new PlaceCalculationRequestException(ErrorType.PLACE_0004);
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
}
