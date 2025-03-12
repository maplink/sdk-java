package global.maplink.tracking.schema.schemaTest;

import global.maplink.geocode.schema.Address;
import global.maplink.geocode.schema.GeoPoint;
import global.maplink.geocode.schema.State;
import global.maplink.json.JsonMapper;
import global.maplink.tracking.schema.domain.Driver;
import global.maplink.tracking.schema.domain.Order;
import global.maplink.tracking.schema.domain.OrderStatus;
import lombok.val;
import lombok.var;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDateTime;

import static global.maplink.tracking.schema.domain.Value.ON_THE_WAY;
import static global.maplink.tracking.schema.errors.ValidationErrorType.*;
import static global.maplink.tracking.schema.testUtils.SampleFiles.TRACKING_ORDER;
import static org.assertj.core.api.Assertions.assertThat;
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
    void isValidateDescriptionNull() {
        Order order = Order.builder()
                .status(OrderStatus.builder()
                        .value(ON_THE_WAY)
                        .label("Pedido em trânsito")
                        .build())
                .destination(Address.builder()
                        .road("Alameda Campinas")
                        .number("579")
                        .city("São Paulo")
                        .zipCode("01419001")
                        .state(new State("SP", "São Paulo"))
                        .mainLocation(GeoPoint.of(
                                BigDecimal.valueOf(-22.7342864),
                                BigDecimal.valueOf(-47.648064)))
                        .build())
                .driver(Driver.builder()
                        .name("Maplink BR")
                        .currentLocation(GeoPoint.of(
                                BigDecimal.valueOf(-22.7342864),
                                BigDecimal.valueOf(-47.648064)))
                        .build())
                .build();

        var errors = order.validate();
        assertEquals(TRACKING_DESCRIPTION_NOTNULL, errors.get(0));
    }

    @Test
    void isValidateDestinationMainLocationNull() {
        Order order = Order.builder()
                .description("Pedido")
                .status(OrderStatus.builder()
                        .value(ON_THE_WAY)
                        .label("Pedido em trânsito")
                        .build())
                .destination(Address.builder()
                        .road("Alameda Campinas")
                        .number("579")
                        .city("São Paulo")
                        .state(new State("SP", "São Paulo"))
                        .zipCode("01419001")
                        .build())
                .driver(Driver.builder()
                        .name("Maplink BR")
                        .currentLocation(GeoPoint.of(
                                BigDecimal.valueOf(-22.7342864),
                                BigDecimal.valueOf(-47.648064)))
                        .build())
                .build();

        var errors = order.validate();
        assertEquals(TRACKING_ADDRESS_MAINLOCATION_NOTNULL, errors.get(0));
    }

    @Test
    void isValidateDestinationDriverNull() {
        Order order = Order.builder()
                .description("Pedido")
                .status(OrderStatus.builder()
                        .value(ON_THE_WAY)
                        .label("Pedido em trânsito")
                        .build())
                .destination(Address.builder()
                        .road("Alameda Campinas")
                        .number("579")
                        .city("São Paulo")
                        .state(new State("SP", "São Paulo"))
                        .zipCode("01419001")
                        .mainLocation(GeoPoint.of(
                                BigDecimal.valueOf(-22.7342864),
                                BigDecimal.valueOf(-47.648064)))
                        .build())
                .driver(Driver.builder()
                        .name("Maplink BR")
                        .build())
                .build();

        var errors = order.validate();
        assertEquals(TRACKING_DRIVER_CURRENTLOCATION_NOTNULL, errors.get(0));
    }

    @Test
    void isValidateDestinationStatusLabelNull() {
        Order order = Order.builder()
                .description("Pedido")
                .status(OrderStatus.builder()
                        .value(ON_THE_WAY)
                        .build())
                .destination(Address.builder()
                        .road("Alameda Campinas")
                        .number("579")
                        .city("São Paulo")
                        .state(new State("SP", "São Paulo"))
                        .zipCode("01419001")
                        .mainLocation(GeoPoint.of(
                                BigDecimal.valueOf(-22.7342864),
                                BigDecimal.valueOf(-47.648064)))
                        .build())
                .driver(Driver.builder()
                        .name("Maplink BR")
                        .currentLocation(GeoPoint.of(
                                BigDecimal.valueOf(-22.7342864),
                                BigDecimal.valueOf(-47.648064)))
                        .build())
                .build();

        var errors = order.validate();
        assertEquals(TRACKING_STATUS_LABEL_NOTNULL, errors.get(0));
    }

    @Test
    void isValidateDestinationStatusValueNull() {
        Order order = Order.builder()
                .description("Pedido")
                .status(OrderStatus.builder()
                        .label("Pedido em trânsito")
                        .build())
                .driver(Driver.builder()
                        .name("Maplink BR")
                        .currentLocation(GeoPoint.of(
                                BigDecimal.valueOf(-22.7342864),
                                BigDecimal.valueOf(-47.648064)))
                        .build())
                .build();

        assertThat(order.validate()).containsExactlyInAnyOrder(
                TRACKING_STATUS_VALUE_NOTNULL,
                TRACKING_DESTINATION_NOTNULL
        );
    }

    @Test
    void isValidateOrderMultipleFieldsNull() {
        val order = Order.builder()
                .status(OrderStatus.builder()
                        .label("Pedido em trânsito")
                        .build())
                .destination(Address.builder()
                        .road("Alameda Campinas")
                        .number("579")
                        .city("São Paulo")
                        .state(new State("SP", "São Paulo"))
                        .zipCode("01419001")
                        .build())
                .driver(Driver.builder()
                        .name("Maplink BR")
                        .build())
                .build();

        assertThat(order.validate())
                .hasSize(4)
                .containsExactlyInAnyOrder(
                        TRACKING_DESCRIPTION_NOTNULL,
                        TRACKING_ADDRESS_MAINLOCATION_NOTNULL,
                        TRACKING_DRIVER_CURRENTLOCATION_NOTNULL,
                        TRACKING_STATUS_VALUE_NOTNULL
                );
    }

}