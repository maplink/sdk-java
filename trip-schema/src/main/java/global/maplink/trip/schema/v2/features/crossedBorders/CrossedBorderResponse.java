package global.maplink.trip.schema.v2.features.crossedBorders;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import static lombok.AccessLevel.PRIVATE;

@Data
@Builder
@RequiredArgsConstructor
@NoArgsConstructor(force = true, access = PRIVATE)
public class CrossedBorderResponse {
    private final String city;
    private final String state;
    private final String country;
}
