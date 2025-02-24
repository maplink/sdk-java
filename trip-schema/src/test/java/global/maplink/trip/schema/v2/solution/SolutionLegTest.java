package global.maplink.trip.schema.v2.solution;

import global.maplink.domain.MaplinkPoint;
import global.maplink.geocode.schema.v1.Address;
import global.maplink.json.JsonMapper;
import global.maplink.place.schema.Category;
import global.maplink.place.schema.LegResult;
import global.maplink.place.schema.PlaceRoute;
import global.maplink.place.schema.SubCategory;
import global.maplink.toll.schema.*;
import global.maplink.toll.schema.result.CalculationDetail;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static global.maplink.trip.testUtils.Defaults.POINT_OFFSET;
import static global.maplink.trip.testUtils.SolutionSampleFiles.SOLUTION_LEG;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

public class SolutionLegTest {

    private final JsonMapper mapper = JsonMapper.loadDefault();

    @Test
    public void shouldDeserialize() {
        SolutionLeg solutionLeg = mapper.fromJson(SOLUTION_LEG.load(), SolutionLeg.class);
        assertEquals(1000L, solutionLeg.getDistance());
        assertEquals(1200L, solutionLeg.getNominalDuration());
        assertEquals(70.0D, solutionLeg.getAverageSpeed());

        assertThat(solutionLeg.getPoints())
                .hasSize(1)
                .first()
                .isEqualTo(new MaplinkPoint(-23.5666499, -46.6557755));

        assertNotNull(solutionLeg.getFirstPointAddress());
        Address address = solutionLeg.getFirstPointAddress();
        assertEquals("Rua Doutor Otávio Teixeira Mendes", address.getRoad());
        assertEquals("Cidade Alta", address.getDistrict());
        assertEquals("13417095", address.getZipCode());
        assertEquals("Piracicaba", address.getCity());
        assertNotNull(address.getState());
        assertEquals("SP", address.getState().getCode());
        assertEquals("São Paulo", address.getState().getName());
        assertNotNull(address.getMainLocation());
        assertEquals(0, new BigDecimal("-22.7331478").compareTo(address.getMainLocation().getLat()));
        assertEquals(0, new BigDecimal("-47.6370854").compareTo(address.getMainLocation().getLon()));

        assertNotNull(solutionLeg.getPlaceCalculation());
        LegResult placeCalculation = solutionLeg.getPlaceCalculation();
        assertEquals(1, placeCalculation.getTotal());
        assertEquals(1, placeCalculation.getPlaces().size());

        PlaceRoute placeRoute = placeCalculation.getPlaces().get(0);
        assertEquals("c4b00106-1d68-49ba-baeb-d72f7c7e35b2", placeRoute.getId());
        assertEquals("MAPLINK", placeRoute.getName());
        assertEquals("95.424.764/0001-10", placeRoute.getDocumentNumber());
        assertEquals(Category.TECNOLOGIA, placeRoute.getCategory());
        assertEquals(SubCategory.DESENVOLVIMENTO_DE_SOFTWARE, placeRoute.getSubCategory());
        assertEquals("https://maplink.global/", placeRoute.getWebsite());
        assertEquals(2, placeRoute.getPhones().size());
        assertTrue(placeRoute.getPhones().contains("(11) 2222-3333"));
        assertTrue(placeRoute.getPhones().contains("(11) 4444-5555"));
        assertEquals("maplinkClientId", placeRoute.getClientId());
        assertTrue(placeRoute.isActive());

        assertNotNull(placeRoute.getAddress());
        assertEquals("Alameda Campinas", placeRoute.getAddress().getStreet());
        assertEquals("579", placeRoute.getAddress().getNumber());
        assertEquals("Jardim Paulista", placeRoute.getAddress().getDistrict());
        assertEquals("São Paulo", placeRoute.getAddress().getCity());
        assertEquals("SP", placeRoute.getAddress().getState());
        assertEquals("01404-100", placeRoute.getAddress().getZipcode());
        assertEquals("9th floor", placeRoute.getAddress().getComplement());
        assertThat(placeRoute.getAddress().getPoint().getLatitude()).isCloseTo(-23.5666499, POINT_OFFSET);
        assertThat(placeRoute.getAddress().getPoint().getLongitude()).isCloseTo(-46.6557755, POINT_OFFSET);

        assertNotNull(solutionLeg.getTollCalculation());
        global.maplink.toll.schema.result.LegResult tollCalculation = solutionLeg.getTollCalculation();

        assertEquals(1, tollCalculation.getTolls().size());
        assertEquals(0, new BigDecimal("209.5").compareTo(tollCalculation.getLegTotalCost()));

        CalculationDetail calculationDetail = tollCalculation.getTolls().get(0);

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
