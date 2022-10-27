package global.maplink.place.schema;

import global.maplink.place.schema.exception.ErrorType;
import global.maplink.place.schema.exception.PlaceCalculationRequestException;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Set;

import static lombok.AccessLevel.PRIVATE;

@Data
@Builder
@RequiredArgsConstructor(staticName = "of")
@NoArgsConstructor(force = true, access = PRIVATE)
public class PlaceRouteRequest {

    public static final int MAX_BUFFER = 500;

    private final Set<Category> categories;
    private final Set<SubCategory> subCategories;
    private final Long bufferRouteInMeters;
    private final Long bufferStoppingPointsInMeters;
    private final boolean onlyMyPlaces;
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
}
