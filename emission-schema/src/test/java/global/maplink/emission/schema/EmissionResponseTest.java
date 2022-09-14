package global.maplink.emission.schema;

import global.maplink.emission.testUtils.SampleFiles;
import global.maplink.json.JsonMapper;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static global.maplink.emission.testUtils.SampleFiles.EMISSION_RESPONSE;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class EmissionResponseTest {

    private final JsonMapper mapper = JsonMapper.loadDefault();

    @Test
    void shouldSerializeJsonFileToEmissionResponseTest(){
        EmissionResponse emissionResponse = mapper.fromJson(EMISSION_RESPONSE.load(), EmissionResponse.class);
        assertEquals(Source.LASTROP_ESALQ, emissionResponse.getSource());
        assertEquals(Fuel.BIODIESEL, emissionResponse.getFuelType());
        assertEquals(0, new BigDecimal("7.08").compareTo(emissionResponse.getFuelConsumed()));
        assertEquals(0, new BigDecimal("34.69").compareTo(emissionResponse.getTotalFuelPrice()));
        assertEquals(0, new BigDecimal("15.146").compareTo(emissionResponse.getTotalEmission()));
    }
}
