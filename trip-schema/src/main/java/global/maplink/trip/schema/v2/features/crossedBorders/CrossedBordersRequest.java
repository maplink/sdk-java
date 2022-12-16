package global.maplink.trip.schema.v2.features.crossedBorders;

import global.maplink.trip.schema.v2.features.reverseGeocode.ReverseGeocodePointsMode;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import static lombok.AccessLevel.PRIVATE;

@Data
@Builder
@RequiredArgsConstructor
@NoArgsConstructor(force = true, access = PRIVATE)
public class CrossedBordersRequest {
    private final CrossedBorderLevel level;
    private final ReverseGeocodePointsMode reverseGeocode;
}
