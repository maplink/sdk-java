package global.maplink.toll.schema.result;

import global.maplink.json.JsonMapper;
import lombok.val;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static global.maplink.toll.testUtils.SampleFiles.CALCULATION_RESULT;
import static java.math.BigDecimal.TEN;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.data.Offset.offset;

class TollCalculationResultTest {
    private static final BigDecimal OFFSET = BigDecimal.valueOf(0.001);

    private final JsonMapper mapper = JsonMapper.loadDefault();

    @Test
    void shouldDeserialize() {
        val data = mapper.fromJson(CALCULATION_RESULT.load(), TollCalculationResult.class);
        assertThat(data.getTotalCost()).isCloseTo(TEN, offset(OFFSET));
        assertThat(data.getLegs()).hasSize(1);

        val firstLeg = data.getLegs().get(0);
        assertThat(firstLeg.getLegTotalCost()).isCloseTo(TEN, offset(OFFSET));
        assertThat(firstLeg.getTolls()).hasSize(1).first().extracting(CalculationDetail::getId).isEqualTo("123");

    }
}