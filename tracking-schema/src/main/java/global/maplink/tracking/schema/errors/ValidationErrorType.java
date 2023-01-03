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
    THEME_COLOR_INCORRECT("color must be a valid hexadecimal value, like #000000"),
    TRACKING_DESCRIPTION_NOTNULL("description cannot be null"),
    TRACKING_DESTINATION_NOTNULL("destination cannot be null"),
    TRACKING_ADDRESS_MAINLOCATION_NOTNULL("at origin and destionation, mainLocation cannot be null"),
    TRACKING_ADDRESS_MAINLOCATION_LATLON_NOTNULL("at origin and destionation, mainLocation lat and lon cannot be null"),
    TRACKING_DRIVER_CURRENTLOCATION_NOTNULL("driver.currentLocation cannot be null"),
    TRACKING_DRIVER_CURRENTLOCATION_LATLON_NOTNULL("at driver.currentLocation lat and lon cannot be null"),
    TRACKING_STATUS_VALUE_NOTNULL("status.value cannot be null"),
    TRACKING_STATUS_LABEL_NOTNULL("status.label cannot be null");

    private final String message;
}