package global.maplink.emission.schema;

import global.maplink.json.JsonMapper;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static global.maplink.emission.testUtils.SampleFiles.EMISSION_RESPONSE;
import static global.maplink.emission.testUtils.SampleFiles.EMISSION_RESPONSE_WITH_FRACTIONS;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class EmissionResponseTest {

    private final JsonMapper mapper = JsonMapper.loadDefault();

    @Test
    void shouldSerializeJsonFileToEmissionResponseTest() {
        EmissionResponse emissionResponse = mapper.fromJson(EMISSION_RESPONSE.load(), EmissionResponse.class);
        assertEquals(0, "LASTROP_ESALQ".compareTo(emissionResponse.getSource()));
        assertEquals(0, "BIODIESEL".compareTo(emissionResponse.getFuelType()));
        assertEquals(0, new BigDecimal("7.08").compareTo(emissionResponse.getFuelConsumed()));
        assertEquals(0, new BigDecimal("34.69").compareTo(emissionResponse.getTotalFuelPrice()));
        assertEquals(0, new BigDecimal("15.146").compareTo(emissionResponse.getTotalEmission()));
        assertNull(emissionResponse.getFractionedEmissionResponses());
    }

    @Test
    void shouldDeserializeFractionedEmissionResponses() {
        EmissionResponse response = mapper.fromJson(EMISSION_RESPONSE_WITH_FRACTIONS.load(), EmissionResponse.class);

        assertThat(response.getFractionedEmissionResponses()).hasSize(2);

        FractionedEmissionResponse client = response.getFractionedEmissionResponses().get(0);
        assertEquals("Cliente", client.getName());
        assertEquals(0, new BigDecimal("1.42").compareTo(client.getFuelConsumed()));
        assertEquals(0, new BigDecimal("6.94").compareTo(client.getTotalFuelPrice()));
        assertEquals(0, new BigDecimal("3.029").compareTo(client.getTotalEmission()));

        FractionedEmissionResponse carrier = response.getFractionedEmissionResponses().get(1);
        assertEquals("Transportadora", carrier.getName());
        assertEquals(0, new BigDecimal("5.66").compareTo(carrier.getFuelConsumed()));
        assertEquals(0, new BigDecimal("27.75").compareTo(carrier.getTotalFuelPrice()));
        assertEquals(0, new BigDecimal("12.117").compareTo(carrier.getTotalEmission()));
    }
}
