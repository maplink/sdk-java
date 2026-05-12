package global.maplink.place.schema;

import global.maplink.domain.MaplinkPoint;
import global.maplink.json.JsonMapper;
import global.maplink.place.schema.exception.PlaceUpdateViolation;
import global.maplink.validations.ValidationException;
import global.maplink.validations.ValidationViolation;
import lombok.val;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.platform.commons.util.StringUtils;

import java.time.DayOfWeek;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static global.maplink.place.schema.PaymentMethod.*;
import static global.maplink.place.testUtils.Defaults.POINT_OFFSET;
import static global.maplink.place.testUtils.SampleFiles.PLACE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

public class PlaceTest {

    private final JsonMapper mapper = JsonMapper.loadDefault();

    @Test
    public void shouldDeserialize() {
        Place place = mapper.fromJson(PLACE.load(), Place.class);
        assertEquals("a98553bc-78e5-4035-a048-74e361625ce1", place.getId());
        assertEquals("MAPLINK", place.getName());
        assertEquals("95.424.764/0001-10", place.getDocumentNumber());
        assertEquals("Maplink Brasil", place.getDescription());
        assertTrue(StringUtils.isBlank(place.getUrl()));
        assertEquals("http://maplink.global", place.getWebsite());
        assertTrue(StringUtils.isBlank(place.getFavicon()));
        assertEquals(4, place.getPayments().size());
        assertTrue(place.getPayments().containsAll(Arrays.asList(BOLETO_BANCARIO, DINHEIRO, MASTERCARD, VISA)));
        assertEquals(Category.TECNOLOGIA, place.getCategory());
        assertEquals(SubCategory.DESENVOLVIMENTO_DE_SOFTWARE, place.getSubCategory());
        assertEquals(2, place.getPhones().size());
        assertTrue(place.getPhones().contains("(11) 2222-3333"));
        assertTrue(place.getPhones().contains("(11) 4444-5555"));
        assertNull(place.getAdditionalInfo());
        assertTrue(place.isActive());
        assertEquals("maplinkClientId", place.getClientId());

        assertEquals(2, place.getConnections().size());
        SocialConnection facebook = SocialConnection.builder().provider("facebook")
                .url("https://www.facebook.com/maplink").build();
        SocialConnection instagram = SocialConnection.builder().provider("instagram")
                .url("https://br.linkedin.com/company/maplink").build();
        assertTrue(place.getConnections().contains(facebook));
        assertTrue(place.getConnections().contains(instagram));

        assertNotNull(place.getOpeningHours());
        assertEquals(5, place.getOpeningHours().getPeriods().size());
        List<OpenHour> periods = place.getOpeningHours().getPeriods();
        periods.forEach(p -> assertEquals("09:00", p.getOpen().getTime()));
        periods.forEach(p -> assertEquals("18:00", p.getClose().getTime()));
        List<DayOfWeek> daysOfWeek = Arrays.asList(DayOfWeek.MONDAY, DayOfWeek.TUESDAY, DayOfWeek.WEDNESDAY,
                DayOfWeek.THURSDAY, DayOfWeek.FRIDAY);
        assertTrue(periods.stream().map(p -> p.getOpen().getDay()).collect(Collectors.toList())
                .containsAll(daysOfWeek));
        assertTrue(periods.stream().map(p -> p.getClose().getDay()).collect(Collectors.toList())
                .containsAll(daysOfWeek));

        assertNotNull(place.getAddress());
        assertEquals("Alameda Campinas", place.getAddress().getStreet());
        assertEquals("579", place.getAddress().getNumber());
        assertEquals("Jardim Paulista", place.getAddress().getDistrict());
        assertEquals("São Paulo", place.getAddress().getCity());
        assertEquals("SP", place.getAddress().getState());
        assertEquals("01404-100", place.getAddress().getZipcode());
        assertEquals("9th floor", place.getAddress().getComplement());
        assertThat(place.getAddress().getPoint().getLatitude()).isCloseTo(-23.5666499, POINT_OFFSET);
        assertThat(place.getAddress().getPoint().getLongitude()).isCloseTo(-46.6557755, POINT_OFFSET);
        assertThat(place.validate()).isEmpty();
        place.throwIfInvalid();
    }

    @Test
    void shouldValidate() {
        val place = Place.builder().build();
        assertThat(place.validate()).isNotEmpty().hasSize(5);

        assertThatThrownBy(place::throwIfInvalid).isInstanceOf(ValidationException.class);
    }

    @ParameterizedTest
    @MethodSource("invalidPlaceProvider")
    void shouldRejectPlaceWithMissingField(Place place, String expectedMessage) {
        List<ValidationViolation> violations = place.validate();

        assertThatThrownBy(place::throwIfInvalid).isInstanceOf(ValidationException.class);
        assertThat(violations).hasSize(1);
        assertThat(violations.get(0)).isInstanceOf(PlaceUpdateViolation.class);
        assertThat(violations.get(0).getMessage()).isEqualTo(expectedMessage);
    }

    @Test
    void shouldCascadeViolationsFromInvalidAddress() {
        Place place = validPlace()
                .address(Address.builder().build())
                .build();

        assertThat(place.validate())
                .hasSize(6)
                .extracting(ValidationViolation::getMessage)
                .containsExactlyInAnyOrder(
                        "Required valid field: address.street",
                        "Required valid field: address.city",
                        "Required valid field: address.state",
                        "Required valid field: address.number",
                        "Required valid field: address.zipcode",
                        "Required valid field: address.point"
                );
    }

    static Stream<Arguments> invalidPlaceProvider() {
        return Stream.of(
                Arguments.of(validPlace().id(null).build(),          "Required valid field: id"),
                Arguments.of(validPlace().id("   ").build(),         "Required valid field: id"),
                Arguments.of(validPlace().name(null).build(),        "Required valid field: name"),
                Arguments.of(validPlace().name("").build(),          "Required valid field: name"),
                Arguments.of(validPlace().category(null).build(),    "Required valid field: category"),
                Arguments.of(validPlace().subCategory(null).build(), "Required valid field: subCategory"),
                Arguments.of(validPlace().address(null).build(),     "Required valid field: address")
        );
    }

    private static Place.PlaceBuilder validPlace() {
        return Place.builder()
                .id("a98553bc-78e5-4035-a048-74e361625ce1")
                .name("MAPLINK")
                .category(Category.TECNOLOGIA)
                .subCategory(SubCategory.DESENVOLVIMENTO_DE_SOFTWARE)
                .address(Address.builder()
                        .street("Alameda Campinas")
                        .number("579")
                        .city("São Paulo")
                        .state("SP")
                        .zipcode("01404-100")
                        .point(new MaplinkPoint(-23.5666499, -46.6557755))
                        .build());
    }
}
