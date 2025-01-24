package global.maplink.toll.schema.result;

import global.maplink.domain.MaplinkPoint;
import global.maplink.json.JsonMapper;
import global.maplink.toll.schema.*;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.Instant;

import static global.maplink.toll.testUtils.SampleFiles.LEG_RESULT;
import static java.time.DayOfWeek.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

public class LegResultTest {

    private final JsonMapper mapper = JsonMapper.loadDefault();

    @Test
    public void shouldDeserialize() {
        LegResult legResult = mapper.fromJson(LEG_RESULT.load(), LegResult.class);

        assertEquals(1, legResult.getTolls().size());
        assertEquals(0, new BigDecimal("209.5").compareTo(legResult.getLegTotalCost()));
        assertEquals(TollVehicleType.CAR, legResult.getVehicleType());

        CalculationDetail calculationDetail = legResult.getTolls().get(0);

        assertEquals("236e9cd5-4181-408c-b90f-a24c31237f11", calculationDetail.getId());
        assertEquals("MAIN", calculationDetail.getName());
        assertEquals("calculationDetailAddress", calculationDetail.getAddress());
        assertEquals("Sao Paulo", calculationDetail.getCity());

        assertNotNull(calculationDetail.getState());
        assertEquals("Sao Paulo", calculationDetail.getState().getName());
        assertEquals("SP", calculationDetail.getState().getCode());

        assertEquals("Brasil", calculationDetail.getCountry());
        assertEquals("IDK", calculationDetail.getConcession());
        assertEquals(TollDirection.NORTH, calculationDetail.getDirection());

        assertThat(calculationDetail.getCoordinates()).isEqualTo(new MaplinkPoint(-23.5666499,-46.6557755));

        assertThat(calculationDetail.getServiceTypes()).hasSize(1).first().satisfies(st -> {
                    assertThat(st.getServiceId()).isEqualTo("236e9cd5-4181-408c-b90f-a24c31237f11");
                    assertThat(st.getName()).isEqualTo("MAPLINK");
                }
        );

        assertThat(calculationDetail.getPrice()).isEqualByComparingTo("59.7");

        assertThat(calculationDetail.getConditions()).hasSize(1);
        TollCondition tollCondition = calculationDetail.getConditions().get(0);

        assertThat(tollCondition.getDaysOfWeek()).containsExactlyInAnyOrder(MONDAY, WEDNESDAY, FRIDAY);

        assertThat(tollCondition.getPeriods()).containsExactlyInAnyOrder(
                TollConditionPeriod.NORMAL,
                TollConditionPeriod.HOLIDAY
        );

        assertThat(tollCondition.getBillingsType()).hasSize(2).containsExactlyInAnyOrder(
                TollConditionBillingType.NORMAL,
                TollConditionBillingType.ADDITIONAL_AXLE
        );

        assertThat(tollCondition.getTimesWindow()).hasSize(2).containsExactlyInAnyOrder("ONE", "TWO");

        assertThat(tollCondition.getTags()).hasSize(1).containsExactlyInAnyOrder("FIRST_TAG");

        assertThat(tollCondition.getVehicleTypes())
                .hasSize(3)
                .containsExactlyInAnyOrder(
                        TollVehicleType.CAR_WITH_THREE_SIMPLE_AXLES,
                        TollVehicleType.BUS_WITH_FIVE_DOUBLE_AXLES,
                        TollVehicleType.TRUCK_WITH_TWO_DOUBLE_AXLES
                );

        assertThat(tollCondition.getRoutes())
                .hasSize(1)
                .containsExactly("ROUTE_ONE");

        assertThat(tollCondition.getValue()).isEqualByComparingTo("149.8");

        assertThat(legResult.getCondition())
                .isEqualTo(new Condition(TollConditionBillingType.TAG, TollConditionPeriod.NORMAL));

        assertThat(legResult.getCalculationDate())
                .isEqualTo(Instant.ofEpochMilli(1710769071000L));
    }
}
