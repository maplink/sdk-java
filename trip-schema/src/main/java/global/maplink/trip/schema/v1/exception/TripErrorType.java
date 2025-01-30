package global.maplink.trip.schema.v1.exception;

import global.maplink.trip.schema.v2.problem.VehicleType;
import global.maplink.validations.ValidationViolation;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum TripErrorType implements ValidationViolation {
    ROUTE_POINTS_LESS_THAN_TWO("The route must contain at least two site points"),
    VARIABLE_AXLES_FROMSITEID_EMPTY("fromSiteId should not be empty"),
    VARIABLE_AXLES_TOSITEID_EMPTY("toSiteId should not be empty"),
    VARIABLE_AXLES_FROMSITEID_POINTING_TO_LAST_SITE("fromSiteId cannot be the last site mentioned in points list"),
    TOSITEID_BEFORE_FROMSITEID("toSiteId cannot come before fromSiteId in the points sequence"),
    MISSING_NEW_VEHICLE_TYPE("variableAxles leg must have a newVehicleType"),
    MSG_CONTAINED_IN("does not have a valid value. Allowed values: "),
    PROFILE_NAME_EMPTY("Field profileName should not be empty"),
    CALCULATION_MODE_FIELD_EMPTY("Field calculationMode should not be empty"),
    CALLBACK_DOES_NOT_HAVE_URL("Callback parameters must have an url"),
    TOLL_PARAMETERS_DOES_NOT_HAVE_VEHICLE_TYPE("Toll parameters must have a vehicleType"),
    CROSSED_BORDERS_DOES_NOT_HAVE_LEVEL("CrossedBorders parameters must have a level"),
    ROAD_TYPE_ELEMENTS_EMPTY("SpeedPreferences should contain one element for each roadType"),
    VEHICLE_SPECIFICATION_CONTAINS_NEGATIVE_VALUE("VehicleSpecification should not contain any measure with a negative value"),
    TRIP_PROFILE_NOT_FOUND("Trip profile does not exist"),
    INVALID_VEHICLE_TYPE("vehicleType does not have a valid value. Possible values are: [CAR, MOTORCYCLE, SMALL_TRUCK, TRUCK, FOOT, BIKE]"),
    VEHICLE_TYPE_WITH_TOLL("Cannot use vehicleType when toll is present");

    private final String message;
}