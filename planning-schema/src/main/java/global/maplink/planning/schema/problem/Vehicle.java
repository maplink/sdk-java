package global.maplink.planning.schema.problem;

import lombok.*;

import java.util.List;

import static lombok.AccessLevel.PRIVATE;

@Data
@Builder
@AllArgsConstructor(access = PRIVATE)
@NoArgsConstructor(force = true)
public class Vehicle {

    private final String name;
    private final String vehicleType;
    private final String legislationProfile;
    @Singular
    private final List<AvailablePeriod> availablePeriods;
    private final Integer priority;
    private final List<String> logisticZones;
}
