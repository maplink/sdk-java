package global.maplink.trip.schema.v1.payload;

import global.maplink.trip.schema.v1.exception.TripErrorType;
import global.maplink.validations.Validable;
import global.maplink.validations.ValidationViolation;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VehicleSpecification implements Validable {
    private Double maxWeight;
    private Double maxWeightPerAxle;
    private Double maxLength;
    private Double maxLengthBetweenAxles;
    private Double maxWidth;
    private Double maxHeight;
    private Double maxWeightForExplodingMaterials;
    private Double maxWeightForPollutingMaterials;
    private Double maxWeightForDangerousMaterials;
    @Singular
    private Set<LoadType> loadTypes;

    public List<ValidationViolation> validate() {
        List<ValidationViolation> errors = new ArrayList<>();

        List<Double> fields = Stream.of(
                        maxWeight,
                        maxWeightPerAxle,
                        maxLength,
                        maxLengthBetweenAxles,
                        maxWidth,
                        maxHeight,
                        maxWeightForExplodingMaterials,
                        maxWeightForPollutingMaterials,
                        maxWeightForDangerousMaterials)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());

        for (Double field : fields){
            if (field < 0) {
                errors.add(TripErrorType.VEHICLE_SPECIFICATION_CONTAINS_NEGATIVE_VALUE);
                break;
            }
        }

        return errors;
    }
}
