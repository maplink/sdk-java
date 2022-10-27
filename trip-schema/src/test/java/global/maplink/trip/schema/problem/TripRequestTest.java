package global.maplink.trip.schema.problem;

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
        assertEquals("MAPLINK", tripRequest.getProfileName());

        assertEquals(1, tripRequest.getPoints().size());
        assertEquals("36cdd555-41b6-4327-ac83-04ac74cff915", tripRequest.getPoints().get(0).getSiteId());
        assertEquals(0, new BigDecimal("-23.5666499").compareTo(tripRequest.getPoints().get(0).getLatitude()));
        assertEquals(0, new BigDecimal("-46.6557755").compareTo(tripRequest.getPoints().get(0).getLongitude()));

        assertEquals("STRICT", tripRequest.getCalculationMode());

        VehicleSpecification vehicleSpecification = tripRequest.getVehicleSpecification();
        assertNotNull(vehicleSpecification);
        assertEquals(1000.0D, vehicleSpecification.getMaxWeight());
        assertEquals(150.0D, vehicleSpecification.getMaxWeightPerAxle());
        assertEquals(20.0D, vehicleSpecification.getMaxLength());
        assertEquals(50.0D, vehicleSpecification.getMaxLengthBetweenAxles());
        assertEquals(7.0D, vehicleSpecification.getMaxWidth());
        assertEquals(4.0D, vehicleSpecification.getMaxHeight());
        assertEquals(10.0D, vehicleSpecification.getMaxWeightForExplodingMaterials());
        assertEquals(5.0D, vehicleSpecification.getMaxWeightForPollutingMaterials());
        assertEquals(3.0D, vehicleSpecification.getMaxWeightForDangerousMaterials());
        assertTrue(vehicleSpecification.getLoadTypes().containsAll(Arrays.asList("LoadTypeOne", "LoadTypeTwo")));

        assertEquals(2, tripRequest.getRestrictionZones().size());
        assertTrue(tripRequest.getRestrictionZones().containsAll(Arrays.asList("ONE", "TWO")));

        assertEquals(2, tripRequest.getAvoidanceTypes().size());
        assertTrue(tripRequest.getAvoidanceTypes().containsAll(Arrays.asList("FIRST", "SECOND")));

        TollRequest tollRequest = tripRequest.getToll();
        assertNotNull(tollRequest);
        assertEquals("TRUCK", tollRequest.getVehicleType());
        assertEquals("CREDIT_CARD", tollRequest.getBilling());

        CrossedBordersRequest crossedBordersRequest = tripRequest.getCrossedBorders();
        assertNotNull(crossedBordersRequest);
        assertEquals("HIGH", crossedBordersRequest.getLevel());
        assertEquals("reverseGeocode", crossedBordersRequest.getReverseGeocode());

        assertEquals(LocalDate.of(2022, 12, 25), tripRequest.getExpireIn());
    }
}
