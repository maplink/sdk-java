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

import java.util.List;
import java.util.stream.Stream;

import static global.maplink.place.testUtils.Defaults.POINT_OFFSET;
import static global.maplink.place.testUtils.SampleFiles.ADDRESS;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class AddressTest {

    private final JsonMapper mapper = JsonMapper.loadDefault();

    @Test
    public void shouldDeserialize() {
        Address address = mapper.fromJson(ADDRESS.load(), Address.class);
        assertEquals("Alameda Campinas", address.getStreet());
        assertEquals("579", address.getNumber());
        assertEquals("Jardim Paulista", address.getDistrict());
        assertEquals("São Paulo", address.getCity());
        assertEquals("SP", address.getState());
        assertEquals("01404-100", address.getZipcode());
        assertEquals("9th floor", address.getComplement());
        assertThat(address.getPoint().getLatitude()).isCloseTo(-23.5666499, POINT_OFFSET);
        assertThat(address.getPoint().getLongitude()).isCloseTo(-46.6557755, POINT_OFFSET);
        assertThat(address.validate()).isEmpty();
    }

    @Test
    void shouldValidate() {
        val address = Address.builder().build();
        assertThat(address.validate()).isNotEmpty().hasSize(6);

        assertThatThrownBy(address::throwIfInvalid).isInstanceOf(ValidationException.class);
    }

    @ParameterizedTest
    @MethodSource("invalidAddressProvider")
    void shouldRejectAddressWithMissingField(Address address, String expectedMessage) {
        List<ValidationViolation> violations = address.validate();

        assertThatThrownBy(address::throwIfInvalid).isInstanceOf(ValidationException.class);
        assertThat(violations).hasSize(1);
        assertThat(violations.get(0)).isInstanceOf(PlaceUpdateViolation.class);
        assertThat(violations.get(0).getMessage()).isEqualTo(expectedMessage);
    }

    static Stream<Arguments> invalidAddressProvider() {
        return Stream.of(
                Arguments.of(validAddress().street(null).build(),  "Required valid field: address.street"),
                Arguments.of(validAddress().street("   ").build(), "Required valid field: address.street"),
                Arguments.of(validAddress().city(null).build(),    "Required valid field: address.city"),
                Arguments.of(validAddress().state(null).build(),   "Required valid field: address.state"),
                Arguments.of(validAddress().number(null).build(),  "Required valid field: address.number"),
                Arguments.of(validAddress().zipcode(null).build(), "Required valid field: address.zipcode"),
                Arguments.of(validAddress().point(null).build(),   "Required valid field: address.point")
        );
    }

    private static Address.AddressBuilder validAddress() {
        return Address.builder()
                .street("Alameda Campinas")
                .number("579")
                .district("Jardim Paulista")
                .city("São Paulo")
                .state("SP")
                .zipcode("01404-100")
                .complement("9th floor")
                .point(new MaplinkPoint(-23.5666499, -46.6557755));
    }
}
