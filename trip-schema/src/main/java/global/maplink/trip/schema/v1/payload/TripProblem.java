package global.maplink.trip.schema.v1.payload;

import global.maplink.emission.schema.EmissionRequest;
import global.maplink.freight.schema.FreightCalculationRequest;
import global.maplink.place.schema.PlaceRouteRequest;
import global.maplink.trip.schema.v1.exception.TripErrorType;
import global.maplink.trip.schema.v1.exception.TripCalculationRequestException;
import global.maplink.trip.schema.v2.features.crossedBorders.CrossedBordersRequest;
import global.maplink.trip.schema.v2.problem.CalculationMode;
import global.maplink.trip.schema.v2.problem.SitePoint;
import global.maplink.trip.schema.v2.problem.TollRequest;
import global.maplink.validations.Validable;
import global.maplink.validations.ValidationViolation;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TripProblem implements Validable {
    private static final int MINIMUM_POINTS = 2;

    private String clientId;
    private String profileName;
    @Singular
    private List<SitePoint> points;
    private CalculationMode calculationMode;
    @Singular
    private List<SpeedPreference> speedPreferences;
    private VehicleSpecification vehicleSpecification;
    @Singular
    private Set<String> restrictionZones;
    @Singular
    private Set<AvoidanceType> avoidanceTypes;
    private Callback callback;
    private TollRequest toll;
    private CrossedBordersRequest crossedBorders;
    private FreightCalculationRequest freight;
    private EmissionRequest emission;
    private PlaceRouteRequest place;

    @Override
    public List<ValidationViolation> validate() {
        List<ValidationViolation> errors = new ArrayList<>();

        if (profileName == null || profileName.isEmpty()) {
            errors.add(TripErrorType.PROFILE_NAME_EMPTY);
        }

        if (calculationMode == null) {
            errors.add(TripErrorType.CALCULATION_MODE_FIELD_EMPTY);
        }

        if (callback != null && callback.getUrl() == null) {
            errors.add(TripErrorType.CALLBACK_DOES_NOT_HAVE_URL);
        }

        if (toll != null && toll.getVehicleType() == null) {
            errors.add(TripErrorType.TOLL_PARAMETERS_DOES_NOT_HAVE_VEHICLE_TYPE);
        }

        if (crossedBorders != null && crossedBorders.getLevel() == null) {
            errors.add(TripErrorType.CROSSED_BORDERS_DOES_NOT_HAVE_LEVEL);
        }

        if (speedPreferences != null && !speedPreferences.isEmpty()) {
            validateSpeedPreferences();
        }

        if (vehicleSpecification != null) {
            vehicleSpecification.validate();
        }

        if (freight != null) {
            errors.addAll(freight.validate());
        }

        if (place != null) {
            errors.addAll(place.validate());
        }

        return errors;
    }

    public void validateWithPoints() {
        if (points == null || points.size() < MINIMUM_POINTS) {
            throw new TripCalculationRequestException(TripErrorType.ROUTE_POINTS_LESS_THAN_TWO);
        }

        validate();
    }

    private void validateSpeedPreferences() {
        final long roadTypesCount = speedPreferences.stream()
                .map(SpeedPreference::getRoadType)
                .distinct()
                .count();

        if (roadTypesCount != RoadType.values().length) {
            throw new TripCalculationRequestException(TripErrorType.ROAD_TYPE_ELEMENTS_EMPTY);
        }
    }

}
