package global.maplink.trip.schema.v1.exception;

import global.maplink.validations.ValidationViolation;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum TripErrorType implements ValidationViolation {
    ROUTE_POINTS_LESS_THAN_TWO("The route must contain at least two site points"),
    PROFILE_NAME_EMPTY("Field profileName should not be empty"),
    CALCULATION_MODE_FIELD_EMPTY("Field calculationMode should not be empty"),
    CALLBACK_DOES_NOT_HAVE_URL("Callback parameters must have an url"),
    TOLL_PARAMETERS_DOES_NOT_HAVE_VEHICLE_TYPE("Toll parameters must have a vehicleType"),
    CROSSED_BORDERS_DOES_NOT_HAVE_LEVEL("CrossedBorders parameters must have a level"),
    ROAD_TYPE_ELEMENTS_EMPTY("SpeedPreferences should contain one element for each roadType"),
    VEHICLE_SPECIFICATION_CONTAINS_NEGATIVE_VALUE("VehicleSpecification should not contain any measure with a negative value"),
    TRIP_PROFILE_NOT_FOUND("Trip profile does not exist");

    private final String message;
}