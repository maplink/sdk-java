package global.maplink.tracking.schema.errors;

import global.maplink.validations.ValidationViolation;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ValidationErrorType implements ValidationViolation {

    THEME_ID_NOTNULL("id cannot be null"),
    THEME_LANGUAGE_NOTNULL("language cannot be null"),
    THEME_COLOR_NOTNULL("color cannot be null"),
    THEME_COLOR_INCORRECT("color is incorrect"),
    TRACKING_DESCRIPTION_NOTNULL("description cannot be null"),
    TRACKING_DESTINATION_ROAD_NOTNULL("road cannot be null"),
    TRACKING_DESTINATION_NUMBER_NOTNULL("number cannot be null"),
    TRACKING_DESTINATION_CITY_NOTNULL("city cannot be null"),
    TRACKING_DESTINATION_ZIPCODE_NOTNULL("cep cannot be null"),
    TRACKING_DESTINATION_STATE_NOTNULL("state cannot be null"),
    TRACKING_GEOPOINT_NOTNULL("lat and lon cannot be null"),
    TRACKING_STATUS_VALUE_NOTNULL("value cannot be null"),
    TRACKING_STATUS_LABEL_NOTNULL("label cannot be null");

    private final String message;
}