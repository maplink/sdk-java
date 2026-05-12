package global.maplink.tracking.schema.schemaTest;

import global.maplink.tracking.schema.domain.OrderStatus;
import global.maplink.tracking.schema.domain.Value;
import global.maplink.tracking.schema.errors.TrackingViolation;
import global.maplink.validations.ValidationException;
import global.maplink.validations.ValidationViolation;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;

import static global.maplink.tracking.schema.domain.Value.ON_THE_WAY;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

class OrderStatusTest {

    @Test
    void shouldValidate() {
        OrderStatus status = OrderStatus.builder()
                .value(ON_THE_WAY)
                .label("pedido saiu para entrega")
                .build();

        assertThat(status.validate()).isEmpty();
    }

    @ParameterizedTest
    @MethodSource("invalidStatusProvider")
    void shouldRejectInvalidStatus(OrderStatus status, String expectedMessage) {
        List<ValidationViolation> violations = status.validate();

        assertThatThrownBy(status::throwIfInvalid).isInstanceOf(ValidationException.class);
        assertThat(violations).hasSize(1);
        assertThat(violations.get(0)).isInstanceOf(TrackingViolation.class);
        assertThat(violations.get(0).getMessage()).isEqualTo(expectedMessage);
    }

    @Test
    void shouldReportAllViolationsWhenAllFieldsAreNull() {
        OrderStatus status = OrderStatus.builder().build();

        assertThat(status.validate())
                .hasSize(2)
                .extracting(ValidationViolation::getMessage)
                .containsExactlyInAnyOrder(
                        "status.value cannot be null",
                        "status.label cannot be null"
                );
    }

    @Test
    void shouldRejectStatusWithBlankLabel() {
        OrderStatus status = OrderStatus.builder()
                .value(ON_THE_WAY)
                .label("   ")
                .build();

        assertThat(status.validate())
                .hasSize(1)
                .extracting(ValidationViolation::getMessage)
                .containsExactly("status.label cannot be null");
    }

    static Stream<Arguments> invalidStatusProvider() {
        return Stream.of(
                Arguments.of(statusWith(ON_THE_WAY, null), "status.label cannot be null"),
                Arguments.of(statusWith(null, "pedido saiu para entrega"), "status.value cannot be null")
        );
    }

    private static OrderStatus statusWith(Value value, String label) {
        return OrderStatus.builder()
                .value(value)
                .label(label)
                .build();
    }
}
