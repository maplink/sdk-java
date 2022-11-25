package global.maplink.tracking.schema.schema.errors;

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
    TRACKING_DESTINATION_NOTNULL("destination cannot be null"),
    TRACKING_DRIVER_GEOPOINT_NOTNULL("lat and lon cannot be null"),
    TRACKING_STATUS_VALUE_NOTNULL("value cannot be null"),
    TRACKING_STATUS_LABEL_NOTNULL("label cannot be null");

    private final String message;
}