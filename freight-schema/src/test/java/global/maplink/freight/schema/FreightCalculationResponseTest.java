package global.maplink.freight.schema;

import global.maplink.json.JsonMapper;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static global.maplink.freight.testUtils.SampleFiles.FREIGHT_CALCULATION_RESPONSE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class FreightCalculationResponseTest {

    private final JsonMapper mapper = JsonMapper.loadDefault();

    @Test
    public void shouldDeserialize() {
        FreightCalculationResponse freightCalculationResponse = mapper.fromJson(FREIGHT_CALCULATION_RESPONSE.load(),
                FreightCalculationResponse.class);

        assertEquals("Maplink Testing", freightCalculationResponse.getSource());
        assertEquals(0, new BigDecimal("145.0").compareTo(freightCalculationResponse.getMinimumFreight()));
        assertEquals(0, new BigDecimal("205.0").compareTo(freightCalculationResponse.getMinimumFreightWithCosts()));
        assertEquals(2, freightCalculationResponse.getResults().size());

        assertTrue(freightCalculationResponse.getResults().containsKey(OperationType.B));
        assertTrue(freightCalculationResponse.getResults().get(OperationType.B).containsKey(0));
        assertTrue(freightCalculationResponse.getResults().get(OperationType.B).get(0).containsKey(GoodsType.FRIGORIFICADA_OU_AQUECIDA));

        FreightCalculationResult resultOne = freightCalculationResponse.getResults()
                .get(OperationType.B).get(0).get(GoodsType.FRIGORIFICADA_OU_AQUECIDA);
        assertEquals(0, new BigDecimal("25.0").compareTo(resultOne.getValue()));
        assertTrue(resultOne.getOtherCosts().containsKey("Cost 1"));
        assertTrue(resultOne.getOtherCosts().containsKey("Cost 2"));
        assertEquals(0, new BigDecimal("5.0").compareTo(resultOne.getOtherCosts().get("Cost 1")));
        assertEquals(0, new BigDecimal("10.0").compareTo(resultOne.getOtherCosts().get("Cost 2")));

        assertTrue(freightCalculationResponse.getResults().get(OperationType.B).get(0).containsKey(GoodsType.CARGA_GERAL));

        FreightCalculationResult resultTwo = freightCalculationResponse.getResults()
                .get(OperationType.B).get(0).get(GoodsType.CARGA_GERAL);
        assertEquals(0, new BigDecimal("55.0").compareTo(resultTwo.getValue()));
        assertTrue(resultTwo.getOtherCosts().containsKey("Cost 1"));
        assertTrue(resultTwo.getOtherCosts().containsKey("Cost 2"));
        assertEquals(0, new BigDecimal("3.0").compareTo(resultTwo.getOtherCosts().get("Cost 1")));
        assertEquals(0, new BigDecimal("5.0").compareTo(resultTwo.getOtherCosts().get("Cost 2")));

        assertTrue(freightCalculationResponse.getResults().containsKey(OperationType.D));
        assertTrue(freightCalculationResponse.getResults().get(OperationType.D).containsKey(0));
        assertTrue(freightCalculationResponse.getResults().get(OperationType.D).get(0).containsKey(GoodsType.PERIGOSA_FRIGORIFICADA));

        FreightCalculationResult resultThree = freightCalculationResponse.getResults()
                .get(OperationType.D).get(0).get(GoodsType.PERIGOSA_FRIGORIFICADA);
        assertEquals(0, new BigDecimal("30.0").compareTo(resultThree.getValue()));
        assertTrue(resultThree.getOtherCosts().containsKey("Cost 1"));
        assertTrue(resultThree.getOtherCosts().containsKey("Cost 2"));
        assertEquals(0, new BigDecimal("15.0").compareTo(resultThree.getOtherCosts().get("Cost 1")));
        assertEquals(0, new BigDecimal("10.0").compareTo(resultThree.getOtherCosts().get("Cost 2")));

        assertTrue(freightCalculationResponse.getResults().get(OperationType.D).get(0).containsKey(GoodsType.GRANEL_LIQUIDO));

        FreightCalculationResult resultFour = freightCalculationResponse.getResults()
                .get(OperationType.D).get(0).get(GoodsType.GRANEL_LIQUIDO);
        assertEquals(0, new BigDecimal("35.0").compareTo(resultFour.getValue()));
        assertTrue(resultFour.getOtherCosts().containsKey("Cost 1"));
        assertTrue(resultFour.getOtherCosts().containsKey("Cost 2"));
        assertEquals(0, new BigDecimal("10.0").compareTo(resultFour.getOtherCosts().get("Cost 1")));
        assertEquals(0, new BigDecimal("2.0").compareTo(resultFour.getOtherCosts().get("Cost 2")));
    }
}
