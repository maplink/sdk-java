package global.maplink.tracking.schema.schemaTest;

import global.maplink.tracking.schema.schema.domain.OrderStatus;
import lombok.var;
import org.junit.jupiter.api.Test;

import static global.maplink.tracking.schema.schema.domain.Value.ON_THE_WAY;
import static global.maplink.tracking.schema.schema.exceptions.ValidationErrorType.*;
import static org.junit.jupiter.api.Assertions.*;

class OrderStatusTest {

    @Test
    public void isValidateOrderStatusLabelNull() {
        OrderStatus status = OrderStatus.builder()
                .value(ON_THE_WAY)
                .build();

        var errors = status.validate();
        assertEquals(TRACKING_STATUS_LABEL_NOTNULL, errors.get(0));
    }

    @Test
    public void isValidateOrderStatusValueNull() {
        OrderStatus status = OrderStatus.builder()
                .label("pedido saiu para entrega")
                .build();

        var errors = status.validate();
        assertEquals(TRACKING_STATUS_VALUE_NOTNULL, errors.get(0));
    }

    @Test
    public void isValidateOrderStatusNull() {
        OrderStatus status = OrderStatus.builder()
                .build();

        var errors = status.validate();
        assertEquals(2, errors.size());
        assertEquals(TRACKING_STATUS_VALUE_NOTNULL, errors.get(0));
        assertEquals(TRACKING_STATUS_LABEL_NOTNULL, errors.get(1));
    }

}