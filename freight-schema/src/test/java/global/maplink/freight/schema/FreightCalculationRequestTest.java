package global.maplink.freight.schema;

import global.maplink.json.JsonMapper;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;

import static global.maplink.freight.testUtils.SampleFiles.FREIGHT_CALCULATION_REQUEST;
import static org.junit.jupiter.api.Assertions.*;

public class FreightCalculationRequestTest {

    private final JsonMapper mapper = JsonMapper.loadDefault();

    @Test
    public void shouldDeserializeFreightCalculationRequestJsonFile(){
        FreightCalculationRequest freightCalculationRequest = mapper.fromJson(FREIGHT_CALCULATION_REQUEST.load(),
                FreightCalculationRequest.class);

        assertTrue(freightCalculationRequest.getOperationType().contains(OperationType.A));
        assertTrue(freightCalculationRequest.getOperationType().contains(OperationType.B));

        assertTrue(freightCalculationRequest.getGoodsType().contains(GoodsType.GRANEL_SOLIDO));
        assertTrue(freightCalculationRequest.getGoodsType().contains(GoodsType.FRIGORIFICADA));
        assertTrue(freightCalculationRequest.getGoodsType().contains(GoodsType.NEOGRANEL));
        assertTrue(freightCalculationRequest.getGoodsType().contains(GoodsType.PERIGOSA_GRANEL_LIQUIDO));
        assertTrue(freightCalculationRequest.getGoodsType().contains(GoodsType.GRANEL_PRESSURIZADA));

        assertTrue(freightCalculationRequest.getAxis().contains(4));
        assertTrue(freightCalculationRequest.getAxis().contains(6));

        assertTrue(freightCalculationRequest.getDate().isEqual(LocalDate.of(2022, 10, 21)));
        assertEquals(0, new BigDecimal("30.3").compareTo(freightCalculationRequest.getDistance()));
        assertFalse(freightCalculationRequest.isRoundTrip());
        assertTrue(freightCalculationRequest.isBackEmpty());
    }
}
