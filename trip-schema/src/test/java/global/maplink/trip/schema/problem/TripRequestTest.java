package global.maplink.trip.schema.problem;

import gloabl.maplink.toll.schema.TollConditionBillingType;
import gloabl.maplink.toll.schema.TollVehicleType;
import global.maplink.json.JsonMapper;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;

import static global.maplink.trip.testUtils.ProblemSampleFiles.TRIP_REQUEST;
import static org.junit.jupiter.api.Assertions.*;

public class TripRequestTest {
    
    private final JsonMapper mapper = JsonMapper.loadDefault();
    
    @Test
    public void shouldSerialize(){
        TripRequest tripRequest = mapper.fromJson(TRIP_REQUEST.load(), TripRequest.class);
        assertEquals(1, tripRequest.getPoints().size());
        assertEquals("36cdd555-41b6-4327-ac83-04ac74cff915", tripRequest.getPoints().get(0).getSiteId());
        assertEquals(0, new BigDecimal("-23.5666499").compareTo(tripRequest.getPoints().get(0).getLatitude()));
        assertEquals(0, new BigDecimal("-46.6557755").compareTo(tripRequest.getPoints().get(0).getLongitude()));

        assertEquals("STRICT", tripRequest.getCalculationMode());

        assertEquals(2, tripRequest.getRestrictionZones().size());
        assertTrue(tripRequest.getRestrictionZones().containsAll(Arrays.asList("ONE", "TWO")));

        assertEquals(2, tripRequest.getAvoidanceTypes().size());
        assertTrue(tripRequest.getAvoidanceTypes().containsAll(Arrays.asList("FIRST", "SECOND")));

        TollRequest tollRequest = tripRequest.getToll();
        assertNotNull(tollRequest);
        assertEquals(TollVehicleType.TRUCK_WITH_TWO_SINGLE_AXIS, tollRequest.getVehicleType());
        assertEquals(TollConditionBillingType.NORMAL, tollRequest.getBilling());

        CrossedBordersRequest crossedBordersRequest = tripRequest.getCrossedBorders();
        assertNotNull(crossedBordersRequest);
        assertEquals("HIGH", crossedBordersRequest.getLevel());
        assertEquals("reverseGeocode", crossedBordersRequest.getReverseGeocode());

        assertEquals(LocalDate.of(2022, 12, 25), tripRequest.getExpireIn());
    }
}
