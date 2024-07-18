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
public class VehicleType {

    private final String name;
    private final Double maxWeight;
    private final Double maxVolume;
    private Integer size;
    private final Integer maxSitesNumber;
    private final List<String> characteristics;
    private final String compartmentAccessMode;
    private final List<CompartmentConfiguration> compartmentConfigurations;
    private final ProblemTrip trip;

    private static final String FIELD_TRIP_CALCULATION_MODE = "trip.calculationMode";
    private static final String FIELD_TRIP_TOLL_VEHICLE_TYPE = "trip.toll.vehicleType";
    private static final Integer VEHICLETYPE_DEFAULT_SIZE_FIELD = 0;

    public List<ValidationViolation> validate() {
        List<ValidationViolation> violations = new LinkedList<>();

        if(size == null){
            size = VEHICLETYPE_DEFAULT_SIZE_FIELD;
        }

        if(isNull(name)){
            violations.add(PlanningUpdateViolation.of("vehicleType.name"));
        }

        if(FieldValidator.isNullOrNegative(maxWeight)){
            violations.add(PlanningUpdateViolation.of("vehicleType.maxWeight"));
        }

        if(FieldValidator.isNullOrNegative(maxVolume)){
            violations.add(PlanningUpdateViolation.of("vehicleType.maxVolume"));
        }

        if(!FieldValidator.isNotNegative(size)){
            violations.add(PlanningUpdateViolation.of("vehicleType.size"));
        }

        if(!FieldValidator.isNotNegative(maxSitesNumber)){
            violations.add(PlanningUpdateViolation.of("vehicleType.maxSitesNumber"));
        }

        if(compartmentConfigurations != null){
            if(!isNull(compartmentAccessMode)){
                violations.add(PlanningUpdateViolation.of("vehicleType.compartmentAcessMode"));
            }
        }

        if(trip != null && !isNull(FIELD_TRIP_CALCULATION_MODE)){
                violations.add(PlanningUpdateViolation.of("vehicleType.FIELD_TRIP_CALCULATION_MODE"));
        }

        if(trip != null && trip.getToll() != null && !isNull(FIELD_TRIP_TOLL_VEHICLE_TYPE)){
                violations.add(PlanningUpdateViolation.of("vehicleType.FIELD_TRIP_TOLL_VEHICLE_TYPE"));
        }

        validateCompartmentConfigurations(violations, compartmentConfigurations);
        //invocar validateCompartmentConfigurations

        return violations;
    }

    private void validateCompartmentConfigurations(List<ValidationViolation> violations, List<CompartmentConfiguration> compartmentConfigurations) {
        if (compartmentConfigurations == null) {
            return;
        }

        for (int i = 0; i < compartmentConfigurations.size(); i++) {
            this.compartmentConfigurations.get(i).validate(violations);
        }

    }
}
