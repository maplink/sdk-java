package global.maplink.freight.schema.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum FreightErrorType {
    REQUIRED_FIELDS_EMPTY("Required field must not be empty");

    private final String message;
}
