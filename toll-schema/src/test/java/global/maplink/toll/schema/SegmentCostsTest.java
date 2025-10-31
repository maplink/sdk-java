package global.maplink.toll.schema;

import global.maplink.json.JsonMapper;
import org.junit.jupiter.api.Test;

import static global.maplink.toll.schema.TollVehicleType.*;
import static global.maplink.toll.testUtils.SampleFiles.SEGMENT_COSTS;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.tuple;

public class SegmentCostsTest {

    private final JsonMapper mapper = JsonMapper.loadDefault();

    @Test
    void shouldDeserialize() {
        SegmentCosts result = mapper.fromJson(SEGMENT_COSTS.load(), SegmentCosts.class);

        assertThat(result.getEntryGantryId()).isEqualTo("3700");
        assertThat(result.getConditions()).isEmpty();
        assertThat(result.getServiceTypes())
                .hasSize(1)
                .first()
                .satisfies(service -> {
                    assertThat(service.getServiceId()).isEqualTo("TESTE");
                    assertThat(service.getName()).isEqualTo("Via Facil");
                });


        assertThat(result.getCosts())
                .hasSize(18)
                .extracting(TollCost::getVehicleType , cost -> cost.getPrices().get(Billing.DEFAULT))
                .containsExactly(
                        tuple(MOTORCYCLE, 1.0),
                        tuple(CAR, 2.0),
                        tuple(TRUCK_WITH_TWO_SINGLE_AXIS, 2.0),
                        tuple(CAR_WITH_THREE_SIMPLE_AXLES, 3.0),
                        tuple(CAR_WITH_FOUR_SIMPLE_AXLES, 4.0),
                        tuple(TRUCK_WITH_TWO_DOUBLE_AXLES, 9.0),
                        tuple(TRUCK_WITH_THREE_DOUBLE_AXLES, 10.0),
                        tuple(TRUCK_WITH_FOUR_DOUBLE_AXLES, 11.0),
                        tuple(TRUCK_WITH_FIVE_DOUBLE_AXLES, 12.0),
                        tuple(TRUCK_WITH_SIX_DOUBLE_AXLES, 13.0),
                        tuple(TRUCK_WITH_SEVEN_DOUBLE_AXLES, 14.0),
                        tuple(TRUCK_WITH_EIGHT_DOUBLE_AXLES, 15.0),
                        tuple(TRUCK_WITH_NINE_DOUBLE_AXLES, 16.0),
                        tuple(TRUCK_WITH_TEN_DOUBLE_AXLES, 17.0),
                        tuple(BUS_WITH_TWO_DOUBLE_AXLES, 5.0),
                        tuple(BUS_WITH_THREE_DOUBLE_AXLES, 6.0),
                        tuple(BUS_WITH_FOUR_DOUBLE_AXLES, 7.0),
                        tuple(BUS_WITH_FIVE_DOUBLE_AXLES, 8.0)
                );
    }
}
