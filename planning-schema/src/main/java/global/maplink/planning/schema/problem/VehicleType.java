package global.maplink.planning.schema.problem;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

import static lombok.AccessLevel.PRIVATE;

@Data
@Builder
@AllArgsConstructor(access = PRIVATE)
@NoArgsConstructor(force = true)
public class VehicleType {

    private final String name;
    private final Double maxWeight;
    private final Double maxVolume;
    private final Integer size;
    private final Integer maxSitesNumber;
    private final List<String> characteristics;
    private final String compartmentAccessMode;
    private final List<CompartmentConfiguration> compartmentConfigurations;
    private final ProblemTrip trip;

}
