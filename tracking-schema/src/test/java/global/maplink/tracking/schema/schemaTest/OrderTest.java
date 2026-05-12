package global.maplink.tracking.schema.schemaTest;

import global.maplink.json.JsonMapper;
import global.maplink.tracking.schema.domain.Order;
import global.maplink.tracking.schema.errors.TrackingViolation;
import global.maplink.tracking.schema.testUtils.SampleFiles;
import global.maplink.validations.ValidationException;
import global.maplink.validations.ValidationViolation;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Stream;

import static global.maplink.tracking.schema.domain.Value.ON_THE_WAY;
import static global.maplink.tracking.schema.testUtils.SampleFiles.TRACKING_ORDER;
import static global.maplink.tracking.schema.testUtils.SampleFiles.TRACKING_ORDER_WITH_BLANK_DESCRIPTION;
import static global.maplink.tracking.schema.testUtils.SampleFiles.TRACKING_ORDER_WITH_DESTINATION_LATLON_NULL;
import static global.maplink.tracking.schema.testUtils.SampleFiles.TRACKING_ORDER_WITH_DRIVER_LATLON_NULL;
import static global.maplink.tracking.schema.testUtils.SampleFiles.TRACKING_ORDER_WITH_NULL_DESCRIPTION;
import static global.maplink.tracking.schema.testUtils.SampleFiles.TRACKING_ORDER_WITH_NULL_DESTINATION;
import static global.maplink.tracking.schema.testUtils.SampleFiles.TRACKING_ORDER_WITH_NULL_DESTINATION_MAIN_LOCATION;
import static global.maplink.tracking.schema.testUtils.SampleFiles.TRACKING_ORDER_WITH_NULL_DRIVER_CURRENT_LOCATION;
import static global.maplink.tracking.schema.testUtils.SampleFiles.TRACKING_ORDER_WITH_NULL_STATUS_LABEL;
import static global.maplink.tracking.schema.testUtils.SampleFiles.TRACKING_ORDER_WITH_NULL_STATUS_VALUE;
import static global.maplink.tracking.schema.testUtils.SampleFiles.TRACKING_ORDER_WITH_ORIGIN_LATLON_NULL;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

class OrderTest {

    private final JsonMapper mapper = JsonMapper.loadDefault();

    @Test
    void shouldSerializeJsonFileToOrderRequestTest() {
        Order order = mapper.fromJson(TRACKING_ORDER.load(), Order.class);

        assertEquals("INTERNAL_ID", order.getId());
        assertEquals("1232132132143438", order.getNumber().toString());
        assertEquals("Product Test", order.getDescription());
        assertEquals(LocalDateTime.of(2022, 11, 22, 10, 0, 0), order.getEstimatedArrival());
        assertEquals("Maplink", order.getCompanyName());
        assertEquals(BigDecimal.valueOf(23.12), order.getTotalValue().getValue());
        assertEquals("BRL", order.getTotalValue().getCurrency());
        assertEquals(ON_THE_WAY, order.getStatus().getValue());
        assertEquals("Alameda Campinas", order.getOrigin().getRoad());
        assertEquals("579", order.getOrigin().getNumber());
        assertEquals("São Paulo", order.getOrigin().getCity());
        assertEquals("SP", order.getOrigin().getState().getCode());
        assertEquals("São Paulo", order.getOrigin().getState().getName());
        assertEquals("01419001", order.getOrigin().getZipCode());
        assertEquals("R. Menina Rosana", order.getDestination().getRoad());
        assertEquals("70", order.getDestination().getNumber());
        assertEquals("Itajaí", order.getDestination().getCity());
        assertEquals("SC", order.getDestination().getState().getCode());
        assertEquals("Santa Catarina", order.getDestination().getState().getName());
        assertEquals("88304250", order.getDestination().getZipCode());
        assertEquals("Pedido em trânsito", order.getStatus().getLabel());
        assertEquals("Maplink BR", order.getDriver().getName());
        assertEquals("http://example.com", order.getDriver().getImage());
        assertEquals(BigDecimal.valueOf(-23.564515), order.getDriver().getCurrentLocation().getLat());
        assertEquals(BigDecimal.valueOf(-46.652681), order.getDriver().getCurrentLocation().getLon());
        assertEquals(Instant.parse("2022-11-28T16:00:00.120Z"), order.getAudit().getCreatedAt());
        assertEquals(Instant.parse("2022-11-22T16:30:00.120Z"), order.getAudit().getUpdatedAt());
        assertEquals(Instant.parse("2022-11-28T16:00:00.120Z"), order.getExpiresIn());
        assertEquals("DEFAULT", order.getTheme());
    }

    @Test
    void shouldValidate() {
        Order order = mapper.fromJson(TRACKING_ORDER.load(), Order.class);
        assertThat(order.validate()).isEmpty();
    }

    @ParameterizedTest
    @MethodSource("invalidOrderProvider")
    void shouldRejectInvalidOrder(SampleFiles sampleFile, String expectedMessage) {
        Order order = mapper.fromJson(sampleFile.load(), Order.class);
        List<ValidationViolation> violations = order.validate();

        assertThatThrownBy(order::throwIfInvalid).isInstanceOf(ValidationException.class);
        assertThat(violations).hasSize(1);
        assertThat(violations.get(0)).isInstanceOf(TrackingViolation.class);
        assertThat(violations.get(0).getMessage()).isEqualTo(expectedMessage);
    }

    @ParameterizedTest
    @MethodSource("invalidOrderProvider")
    void shouldSerializeViolationMessageCorrectly(SampleFiles sampleFile, String expectedMessage) {
        Order order = mapper.fromJson(sampleFile.load(), Order.class);
        String serialized = mapper.toJsonString(order.validate());
        assertThat(serialized).contains("\"message\":\"" + expectedMessage + "\"");
    }



    static Stream<Arguments> invalidOrderProvider() {
        return Stream.of(
                Arguments.of(TRACKING_ORDER_WITH_NULL_DESCRIPTION,                "description cannot be null"),
                Arguments.of(TRACKING_ORDER_WITH_BLANK_DESCRIPTION,               "description cannot be null"),
                Arguments.of(TRACKING_ORDER_WITH_NULL_DESTINATION,                "destination cannot be null"),
                Arguments.of(TRACKING_ORDER_WITH_NULL_DESTINATION_MAIN_LOCATION,  "at origin and destionation, mainLocation cannot be null"),
                Arguments.of(TRACKING_ORDER_WITH_DESTINATION_LATLON_NULL,         "at origin and destionation, mainLocation lat and lon cannot be null"),
                Arguments.of(TRACKING_ORDER_WITH_ORIGIN_LATLON_NULL,              "at origin and destionation, mainLocation lat and lon cannot be null"),
                Arguments.of(TRACKING_ORDER_WITH_NULL_DRIVER_CURRENT_LOCATION,    "driver.currentLocation cannot be null"),
                Arguments.of(TRACKING_ORDER_WITH_DRIVER_LATLON_NULL,              "at driver.currentLocation lat and lon cannot be null"),
                Arguments.of(TRACKING_ORDER_WITH_NULL_STATUS_VALUE,               "status.value cannot be null"),
                Arguments.of(TRACKING_ORDER_WITH_NULL_STATUS_LABEL,               "status.label cannot be null")
        );
    }
}
