package global.maplink.freight.schema;

import com.fasterxml.jackson.databind.ObjectMapper;
import global.maplink.json.JsonMapper;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import static global.maplink.freight.testUtils.SampleFiles.FREIGHT_CALCULATION_REQUEST;
import static global.maplink.freight.testUtils.SampleFiles.FREIGHT_CALCULATION_RESULT;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class FreightCalculationResultTest {

    private final JsonMapper mapper = JsonMapper.loadDefault();

    @Test
    public void shouldSerialize() {
        FreightCalculationResult freightCalculationResult = mapper.fromJson(FREIGHT_CALCULATION_RESULT.load(),
                FreightCalculationResult.class);

        assertEquals(0, new BigDecimal("35.0").compareTo(freightCalculationResult.getValue()));

        assertEquals(2, freightCalculationResult.getOtherCosts().size());
        assertTrue(freightCalculationResult.getOtherCosts().containsKey("Cost 1"));
        assertEquals(0, new BigDecimal("10.0").compareTo(freightCalculationResult.getOtherCosts().get("Cost 1")));
        assertTrue(freightCalculationResult.getOtherCosts().containsKey("Cost 2"));
        assertEquals(0, new BigDecimal("25.0").compareTo(freightCalculationResult.getOtherCosts().get("Cost 2")));
    }
}
