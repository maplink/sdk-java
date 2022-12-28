package global.maplink.toll.schema;

import global.maplink.json.JsonMapper;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static global.maplink.toll.testUtils.SampleFiles.TOLL_CONDITION;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TollConditionTest {

    private final JsonMapper mapper = JsonMapper.loadDefault();

    @Test
    public void shouldDeserialize(){
        TollCondition tollCondition = mapper.fromJson(TOLL_CONDITION.load(), TollCondition.class);

        List<DayOfWeek> daysOfWeek = Arrays.asList(DayOfWeek.MONDAY, DayOfWeek.WEDNESDAY, DayOfWeek.FRIDAY);
        assertEquals(daysOfWeek.size(), tollCondition.getDaysOfWeek().size());
        assertTrue(tollCondition.getDaysOfWeek().containsAll(daysOfWeek));

        List<TollConditionPeriod> periods = Arrays.asList(TollConditionPeriod.NORMAL, TollConditionPeriod.HOLIDAY);
        assertEquals(periods.size(), tollCondition.getPeriods().size());
        assertTrue(tollCondition.getPeriods().containsAll(periods));

        List<TollConditionBillingType> billingsType = Arrays.asList(TollConditionBillingType.NORMAL, TollConditionBillingType.ADDITIONAL_AXLE);
        assertEquals(billingsType.size(), tollCondition.getBillingsType().size());
        assertTrue(tollCondition.getBillingsType().containsAll(billingsType));

        List<String> timesWindow = Arrays.asList("ONE", "TWO");
        assertEquals(timesWindow.size(), tollCondition.getTimesWindow().size());
        assertTrue(tollCondition.getTimesWindow().containsAll(timesWindow));

        List<String> tags = Collections.singletonList("FIRST_TAG");
        assertEquals(tags.size(), tollCondition.getTags().size());
        assertTrue(tollCondition.getTags().containsAll(tags));

        List<TollVehicleType> vehicleTypes = Arrays.asList(TollVehicleType.CAR_WITH_THREE_SIMPLE_AXLES,
                TollVehicleType.BUS_WITH_FIVE_DOUBLE_AXLES, TollVehicleType.TRUCK_WITH_TWO_DOUBLE_AXLES);
        assertEquals(vehicleTypes.size(), tollCondition.getVehicleTypes().size());
        assertTrue(tollCondition.getVehicleTypes().containsAll(vehicleTypes));

        List<String> routes = Collections.singletonList("ROUTE_ONE");
        assertEquals(routes.size(), tollCondition.getRoutes().size());
        assertTrue(tollCondition.getRoutes().containsAll(routes));

        assertEquals(0, new BigDecimal("149.8").compareTo(tollCondition.getValue()));
    }
}
