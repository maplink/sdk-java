package global.maplink.place.schema;

import global.maplink.json.JsonMapper;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static global.maplink.place.testUtils.SampleFiles.LEGRESULT;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class LegResultTest {

    private final JsonMapper mapper = JsonMapper.loadDefault();

    @Test
    public void shouldSerialize(){
        LegResult legResult = mapper.fromJson(LEGRESULT.load(), LegResult.class);
        assertEquals(1, legResult.getTotal());
        assertEquals(1, legResult.getPlaces().size());

        PlaceRoute placeRoute = legResult.getPlaces().get(0);
        assertEquals("c4b00106-1d68-49ba-baeb-d72f7c7e35b2", placeRoute.getId());
        assertEquals("MAPLINK", placeRoute.getName());
        assertEquals("95.424.764/0001-10", placeRoute.getDocumentNumber());
        assertEquals(Category.TECNOLOGIA, placeRoute.getCategory());
        assertEquals(SubCategory.DESENVOLVIMENTO_DE_SOFTWARE, placeRoute.getSubCategory());
        assertEquals("https://maplink.global/", placeRoute.getWebsite());
        assertEquals(2, placeRoute.getPhones().size());
        assertTrue(placeRoute.getPhones().contains("(11) 2222-3333"));
        assertTrue(placeRoute.getPhones().contains("(11) 4444-5555"));
        assertEquals("maplinkClientId", placeRoute.getClientId());
        assertTrue(placeRoute.isActive());

        assertNotNull(placeRoute.getAddress());
        assertEquals("Alameda Campinas", placeRoute.getAddress().getStreet());
        assertEquals("579", placeRoute.getAddress().getNumber());
        assertEquals("Jardim Paulista", placeRoute.getAddress().getDistrict());
        assertEquals("São Paulo", placeRoute.getAddress().getCity());
        assertEquals("SP", placeRoute.getAddress().getState());
        assertEquals("01404-100", placeRoute.getAddress().getZipcode());
        assertEquals("9th floor", placeRoute.getAddress().getComplement());
        assertEquals(0, new BigDecimal("-23.5666499").compareTo(placeRoute.getAddress().getPoint().getLatitude()));
        assertEquals(0, new BigDecimal("-46.6557755").compareTo(placeRoute.getAddress().getPoint().getLongitude()));
    }
}
