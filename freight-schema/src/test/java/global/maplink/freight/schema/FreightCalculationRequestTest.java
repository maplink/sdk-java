package global.maplink.freight.schema;

import global.maplink.freight.schema.exception.FreightErrorType;
import global.maplink.json.JsonMapper;
import global.maplink.validations.ValidationViolation;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;

import static global.maplink.freight.testUtils.SampleFiles.FREIGHT_CALCULATION_REQUEST;
import static java.util.Collections.singleton;
import static org.assertj.core.api.Assertions.assertThat;
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

    @Test
    void shouldValidateWithoutViolationsWhenRequiredFieldsArePresent() {
        FreightCalculationRequest request = FreightCalculationRequest.builder()
                .date(LocalDate.of(2022, 10, 21))
                .operationType(singleton(OperationType.A))
                .goodsType(singleton(GoodsType.GRANEL_SOLIDO))
                .axis(singleton(4))
                .distance(new BigDecimal("30.3"))
                .build();

        assertThat(request.validate()).isEmpty();
    }

    @Test
    void shouldReturnViolationMessagesWhenRequiredFieldsAreMissing() {
        FreightCalculationRequest request = FreightCalculationRequest.builder().build();

        assertThat(request.validate())
                .extracting(ValidationViolation::getMessage)
                .containsExactlyInAnyOrder(
                        FreightErrorType.DATE_FIELD_EMPTY.getMessage(),
                        FreightErrorType.OPERATION_TYPE_FIELD_EMPTY.getMessage(),
                        FreightErrorType.GOODS_TYPE_FIELD_EMPTY.getMessage(),
                        FreightErrorType.AXIS_FIELD_EMPTY.getMessage()
                );
    }
}
