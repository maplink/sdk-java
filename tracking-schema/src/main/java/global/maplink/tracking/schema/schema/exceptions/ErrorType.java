package global.maplink.tracking.schema.schema.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorType {

    TRACKING_01("Color cannot be null"),
    TRACKING_02("color is incorrect");

    private final String message;
}