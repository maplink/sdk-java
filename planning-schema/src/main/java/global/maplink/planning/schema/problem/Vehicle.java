package global.maplink.planning.schema.problem;

import global.maplink.planning.schema.exception.PlanningUpdateViolation;
import global.maplink.planning.schema.validator.FieldValidator;
import global.maplink.validations.ValidationViolation;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.LinkedList;
import java.util.List;

import static java.util.Objects.isNull;
import static lombok.AccessLevel.PRIVATE;

@Data
@Builder
@AllArgsConstructor(access = PRIVATE)
@NoArgsConstructor(force = true)
public class Vehicle {

    private final String name;
    private final String vehicleType;
    private final String legislationProfile;
    private final List<AvailablePeriod> availablePeriods;
    private final Integer priority;
    private final List<String> logisticZones;

    public void validate() {
        List<ValidationViolation> violations = new LinkedList<>();

        if(this == null){
            return;
        }

        if(FieldValidator.isInvalid(name)){
            violations.add(PlanningUpdateViolation.of("vehicle.name"));
        }

        if(FieldValidator.isInvalid(vehicleType)){
            violations.add(PlanningUpdateViolation.of("vehicle.vehicleType"));
        }

        if(FieldValidator.isInvalid(legislationProfile)){
            violations.add(PlanningUpdateViolation.of("vehicle.legislationProfile"));
        }

        if(isNull(availablePeriods)){
            violations.add(PlanningUpdateViolation.of("vehicle.availablePeriods"));
        }

        if(availablePeriods.isEmpty()){
            violations.add(PlanningUpdateViolation.of("vehicle.availablePeriods"));
        }

        if(FieldValidator.isNotNegative(priority)){
            violations.add(PlanningUpdateViolation.of("vehicle.priority"));
        }
    }
}
