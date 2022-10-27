package global.maplink.place.schema;

import global.maplink.json.JsonMapper;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static global.maplink.place.testUtils.SampleFiles.ADDRESS;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class AddressTest {

    private final JsonMapper mapper = JsonMapper.loadDefault();

    @Test
    public void shouldSerialize(){
        Address address = mapper.fromJson(ADDRESS.load(), Address.class);
        assertEquals("Alameda Campinas", address.getStreet());
        assertEquals("579", address.getNumber());
        assertEquals("Jardim Paulista", address.getDistrict());
        assertEquals("São Paulo", address.getCity());
        assertEquals("SP", address.getState());
        assertEquals("01404-100", address.getZipcode());
        assertEquals("9th floor", address.getComplement());
        assertEquals(0, new BigDecimal("-23.5666499").compareTo(address.getPoint().getLatitude()));
        assertEquals(0, new BigDecimal("-46.6557755").compareTo(address.getPoint().getLongitude()));
    }
}
