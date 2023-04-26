package global.maplink.place.schema;

import global.maplink.json.JsonMapper;
import global.maplink.validations.ValidationException;
import lombok.val;
import org.junit.jupiter.api.Test;

import static global.maplink.place.testUtils.Defaults.POINT_OFFSET;
import static global.maplink.place.testUtils.SampleFiles.ADDRESS;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class AddressTest {

    private final JsonMapper mapper = JsonMapper.loadDefault();

    @Test
    public void shouldDeserialize(){
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
}
