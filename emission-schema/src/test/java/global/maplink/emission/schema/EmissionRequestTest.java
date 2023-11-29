package global.maplink.emission.schema;

import global.maplink.json.JsonMapper;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static global.maplink.emission.testUtils.SampleFiles.EMISSION_REQUEST;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class EmissionRequestTest {

    private final JsonMapper mapper = JsonMapper.loadDefault();

    @Test
    void shouldSerializeJsonFileToEmissionRequestTest(){
        EmissionRequest emissionRequest = mapper.fromJson(EMISSION_REQUEST.load(), EmissionRequest.class);
        assertEquals("LASTROP_ESALQ", emissionRequest.getSource());
        assertEquals("BIODIESEL", emissionRequest.getFuelType());
        assertEquals(0, new BigDecimal("11.3").compareTo(emissionRequest.getAutonomy()));
        assertEquals(0, new BigDecimal("4.9").compareTo(emissionRequest.getFuelPrice()));
        assertEquals(80000, emissionRequest.getTotalDistance());
    }
}
