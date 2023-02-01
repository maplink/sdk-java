package global.maplink.freight.schema.exception;

import global.maplink.validations.ValidationViolation;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum FreightErrorType implements ValidationViolation {
    DISTANCE_FIELD_EMPTY("Field distance should not be empty"),
    DATE_FIELD_EMPTY("Field date should not be empty"),
    OPERATION_TYPE_FIELD_EMPTY("Field operationType should not be empty"),
    GOODS_TYPE_FIELD_EMPTY("Field goodsType should not be empty"),
    AXIS_FIELD_EMPTY("Field axis should not be empty");

    private final String message;

    public String getMessage() {
        return this.message;
    }
}
