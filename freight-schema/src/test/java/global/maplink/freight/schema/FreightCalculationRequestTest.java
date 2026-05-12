package global.maplink.freight.schema;

import global.maplink.freight.testUtils.SampleFiles;
import global.maplink.json.JsonMapper;
import global.maplink.validations.ValidationException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.stream.Stream;

import static global.maplink.freight.testUtils.SampleFiles.*;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
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
    void shouldPassValidation() {
        FreightCalculationRequest request = mapper.fromJson(FREIGHT_CALCULATION_REQUEST.load(), FreightCalculationRequest.class);
        assertThat(request.validate()).isEmpty();
    }

    @ParameterizedTest
    @MethodSource("invalidRequestProvider")
    void shouldRejectInvalidRequest(SampleFiles sampleFile, String expectedMessage) {
        FreightCalculationRequest request = mapper.fromJson(sampleFile.load(), FreightCalculationRequest.class);
        assertThatThrownBy(request::throwIfInvalid).isInstanceOf(ValidationException.class);
        assertThat(request.validate()).hasSize(1);
        assertThat(request.validate().get(0).getMessage()).isEqualTo(expectedMessage);
    }

    @ParameterizedTest
    @MethodSource("invalidRequestProvider")
    void shouldSerializeViolationMessageCorrectly(SampleFiles sampleFile, String expectedMessage) {
        FreightCalculationRequest request = mapper.fromJson(sampleFile.load(), FreightCalculationRequest.class);
        String serialized = mapper.toJsonString(request.validate());
        assertThat(serialized).contains("\"message\":\"" + expectedMessage + "\"");
    }

    static Stream<Arguments> invalidRequestProvider() {
        return Stream.of(
                Arguments.of(FREIGHT_CALCULATION_REQUEST_WITH_NULL_DATE,           "Required field must not be empty: freight.date"),
                Arguments.of(FREIGHT_CALCULATION_REQUEST_WITH_NULL_OPERATION_TYPE, "Required field must not be empty: freight.operationType"),
                Arguments.of(FREIGHT_CALCULATION_REQUEST_WITH_NULL_GOODS_TYPE,     "Required field must not be empty: freight.goodsType"),
                Arguments.of(FREIGHT_CALCULATION_REQUEST_WITH_NULL_AXIS,           "Required field must not be empty: freight.axis")
        );
    }
}
