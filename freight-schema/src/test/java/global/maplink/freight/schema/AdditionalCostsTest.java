package global.maplink.freight.schema;

import global.maplink.json.JsonMapper;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static global.maplink.freight.testUtils.SampleFiles.ADDITIONAL_COSTS;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class AdditionalCostsTest {

    private final JsonMapper mapper = JsonMapper.loadDefault();

    @Test
    public void shouldDeserializeAdditionalCostsJsonFile(){
        AdditionalCosts additionalCosts = mapper.fromJson(ADDITIONAL_COSTS.load(), AdditionalCosts.class);
        assertEquals("TAX", additionalCosts.getName());
        assertEquals(CostType.FIXED, additionalCosts.getType());
        assertEquals(0, new BigDecimal("250.6").compareTo(additionalCosts.getValue()));
    }
}
