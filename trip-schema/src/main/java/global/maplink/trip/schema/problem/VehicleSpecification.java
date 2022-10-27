package global.maplink.trip.schema.problem;

import lombok.*;

import java.util.Set;

import static lombok.AccessLevel.PRIVATE;


@Data
@Builder
@RequiredArgsConstructor
@NoArgsConstructor(force = true, access = PRIVATE)
public class VehicleSpecification {
    private final Double maxWeight;
    private final Double maxWeightPerAxle;
    private final Double maxLength;
    private final Double maxLengthBetweenAxles;
    private final Double maxWidth;
    private final Double maxHeight;
    private final Double maxWeightForExplodingMaterials;
    private final Double maxWeightForPollutingMaterials;
    private final Double maxWeightForDangerousMaterials;
    private final Set<String> loadTypes;
}
