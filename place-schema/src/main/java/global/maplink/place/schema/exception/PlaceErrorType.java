package global.maplink.place.schema.exception;

import global.maplink.validations.ValidationViolation;
import lombok.AllArgsConstructor;
import lombok.Getter;

import static global.maplink.place.schema.PlaceRouteRequest.MAX_BUFFER;

@Getter
@AllArgsConstructor
public enum PlaceErrorType implements ValidationViolation {
    ROUTE_BUFFER_LESS_THAN_ZERO("The route buffer should be bigger than zero"),
    STOPPING_POINTS_LESS_THAN_ZERO("The stopping points buffer should be bigger than zero"),
    CATEGORY_SUBCATEGORY_NECESSARY("Category or subcategory info is necessary"),
    LEGS_INFO_NEEDED("Legs info is necessary"),
    ROUTE_BUFFER_BIGGER_THAN_MAX_BUFFER("The route buffer should be less than " + MAX_BUFFER),
    STOPPING_POINTS_BIGGER_THAN_MAX_BUFFER("The stopping points buffer should be less than " + MAX_BUFFER),
    REQUIRED_FIELDS_INVALID("Required valid field"),
    REQUIRED_FIELD_STATE_INVALID("Required valid field state"),
    REQUIRED_FIELD_CITY_INVALID("Required valid field city");

    private final String message;
}
