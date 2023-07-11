package global.maplink.trip.schema.v1.exception;

import global.maplink.validations.ValidationViolation;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum TripErrorType implements ValidationViolation {
    ROUTE_POINTS_LESS_THAN_TWO("The route must contain at least two site points"),
    VARIABLE_AXLES_FROMSITEID_EMPTY("fromSiteId should not be empty"),
    VARIABLE_AXLES_TOSITEID_EMPTY("toSiteId should not be empty"),
    VARIABLE_AXLES_FROMSITEID_POINTING_TO_LAST_SITE("fromSiteId cannot be equal to the last site in trip problem"),
    VARIABLE_AXLES_FROMSITEID_SAME_AS_TOSITEID("fromSiteId and toSiteId cannot be the same: "),
    VARIABLE_AXLES_OVERLAP_FOUND("VariableAxles leg overlap found on "),
    MSG_SITEID_NOT_FOUND_IN_PROBLEM("siteId not found in trip problem sites: "),
    MSG_CONTAINED_IN("does not have a valid value. Allowed values: "),
    PROFILE_NAME_EMPTY("Field profileName should not be empty"),
    CALCULATION_MODE_FIELD_EMPTY("Field calculationMode should not be empty"),
    CALLBACK_DOES_NOT_HAVE_URL("Callback parameters must have an url"),
    TOLL_PARAMETERS_DOES_NOT_HAVE_VEHICLE_TYPE("Toll parameters must have a vehicleType"),
    CROSSED_BORDERS_DOES_NOT_HAVE_LEVEL("CrossedBorders parameters must have a level"),
    ROAD_TYPE_ELEMENTS_EMPTY("SpeedPreferences should contain one element for each roadType"),
    VEHICLE_SPECIFICATION_CONTAINS_NEGATIVE_VALUE("VehicleSpecification should not contain any measure with a negative value"),
    TEMPORARY(""),
    TRIP_PROFILE_NOT_FOUND("Trip profile does not exist");

    private String message;

    public TripErrorType addToMessage(String textToadd){
        TEMPORARY.message = this.message + textToadd;
        return TEMPORARY;
    }
}