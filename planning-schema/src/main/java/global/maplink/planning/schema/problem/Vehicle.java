package global.maplink.planning.schema.problem;

import global.maplink.place.schema.exception.PlaceUpdateViolation;
import global.maplink.validations.Validable;
import global.maplink.validations.ValidationViolation;
import lombok.*;

import java.util.LinkedList;
import java.util.List;

import static java.util.Objects.isNull;
import static lombok.AccessLevel.PRIVATE;

@Data
@Builder
@AllArgsConstructor(access = PRIVATE)
@NoArgsConstructor(force = true)
public class Vehicle implements Validable {

    private final String name;
    private final String vehicleType;
    private final String legislationProfile;
    @Singular
    private final List<AvailablePeriod> availablePeriods;
    private final Integer priority;
    private final List<String> logisticZones;


    public List<ValidationViolation> validate() {
        List<ValidationViolation> violations = new LinkedList<>();
        if (isInvalid(name)) {
            violations.add(PlaceUpdateViolation.of("vehicle.name"));
        }

        if (isInvalid(vehicleType)) {
            violations.add(PlaceUpdateViolation.of("vehicle.vehicleType"));
        }

        if (isInvalid(legislationProfile)) {
            violations.add(PlaceUpdateViolation.of("vehicle.legislationProfile"));
        }

        if (isNull(priority)) {
            violations.add(PlaceUpdateViolation.of("vehicle.priority"));
        }

        if (isNull(availablePeriods)) {
            violations.add(PlaceUpdateViolation.of("vehicle.availablePeriods"));
        }

        if (isNull(logisticZones)) {
            violations.add(PlaceUpdateViolation.of("vehicle.logisticZones"));
        }

        return violations;
    }

    private boolean isInvalid(final String value) {
        return isNull(value) || value.trim().isEmpty();
    }
}
