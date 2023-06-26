package global.maplink.toll.schema.result;

import global.maplink.json.JsonMapper;
import global.maplink.toll.schema.*;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static global.maplink.toll.testUtils.SampleFiles.LEG_RESULT;
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

        assertNotNull(calculationDetail.getCoordinates());
        assertEquals(0, new BigDecimal("-23.5666499").compareTo(calculationDetail.getCoordinates().getLatitude()));
        assertEquals(0, new BigDecimal("-46.6557755").compareTo(calculationDetail.getCoordinates().getLongitude()));

        assertEquals(1, calculationDetail.getServiceTypes().size());
        assertEquals("236e9cd5-4181-408c-b90f-a24c31237f11", calculationDetail.getServiceTypes().get(0).getServiceId());
        assertEquals("MAPLINK", calculationDetail.getServiceTypes().get(0).getName());

        assertEquals(0, new BigDecimal("59.7").compareTo(calculationDetail.getPrice()));

        assertEquals(1, calculationDetail.getConditions().size());
        TollCondition tollCondition = calculationDetail.getConditions().get(0);

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
