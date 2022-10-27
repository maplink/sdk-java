package global.maplink.place.schema.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

import static global.maplink.place.schema.PlaceRouteRequest.MAX_BUFFER;

@Getter
@AllArgsConstructor
public enum ErrorType {
    PLACE_0001("The route buffer should be bigger than zero"),
    PLACE_0002("The stopping points buffer should be bigger than zero"),
    PLACE_0003("Category or subcategory info is necessary"),
    PLACE_0004("Legs info is necessary"),
    PLACE_0005("The route buffer should be less than " + MAX_BUFFER),
    PLACE_0006("The stopping points buffer should be less than " + MAX_BUFFER),
    PLACE_0007("Required valid field")
    ;

    private final String message;
}
