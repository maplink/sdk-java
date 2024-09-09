package global.maplink.trip.schema.v2.problem;

import global.maplink.json.JsonMapper;
import global.maplink.toll.schema.Billing;
import global.maplink.toll.schema.TollVehicleType;
import global.maplink.trip.schema.v2.features.crossedBorders.CrossedBordersRequest;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static global.maplink.trip.schema.v1.payload.AvoidanceType.BRIDGES;
import static global.maplink.trip.schema.v1.payload.AvoidanceType.TUNNELS;
import static global.maplink.trip.schema.v2.features.crossedBorders.CrossedBorderLevel.CITY;
import static global.maplink.trip.schema.v2.features.reverseGeocode.ReverseGeocodePointsMode.START_END;
import static global.maplink.trip.schema.v2.features.turnByTurn.Languages.PT_BR;
import static global.maplink.trip.schema.v2.problem.CalculationMode.THE_FASTEST;
import static global.maplink.trip.schema.v2.problem.CalculationMode.THE_SHORTEST;
import static global.maplink.trip.testUtils.ProblemSampleFiles.*;
import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class TripCalculateRequestTest {
    private final JsonMapper mapper = JsonMapper.loadDefault();

    @Test
    void shouldDeserialize() {
        TripCalculateRequest tripRequest = mapper.fromJson(TRIP_REQUEST.load(), TripCalculateRequest.class);
        assertEquals(1, tripRequest.getPoints().size());
        assertEquals("36cdd555-41b6-4327-ac83-04ac74cff915", tripRequest.getPoints().get(0).getSiteId());
        assertEquals(0, new BigDecimal("-23.5666499").compareTo(tripRequest.getPoints().get(0).getLatitude()));
        assertEquals(0, new BigDecimal("-46.6557755").compareTo(tripRequest.getPoints().get(0).getLongitude()));

        assertEquals(CalculationMode.THE_FASTEST, tripRequest.getCalculationMode());

        assertEquals(2, tripRequest.getRestrictionZones().size());
        assertTrue(tripRequest.getRestrictionZones().containsAll(asList("ONE", "TWO")));

        assertEquals(2, tripRequest.getAvoidanceTypes().size());
        assertThat(tripRequest.getAvoidanceTypes()).containsExactlyInAnyOrder(TUNNELS, BRIDGES);

        TollRequest tollRequest = tripRequest.getToll();
        assertNotNull(tollRequest);
        assertEquals(TollVehicleType.TRUCK_WITH_TWO_SINGLE_AXIS, tollRequest.getVehicleType());
        assertEquals(Billing.DEFAULT, tollRequest.getBilling());

        CrossedBordersRequest crossedBordersRequest = tripRequest.getCrossedBorders();
        assertNotNull(crossedBordersRequest);
        assertEquals(CITY, crossedBordersRequest.getLevel());
        assertEquals(START_END, crossedBordersRequest.getReverseGeocode());

        assertThat(tripRequest.getExpireIn()).isEqualTo("2022-12-25T00:00:00-03:00");
    }

    @Test
    void shouldDeserializeCalculationModeDefault() {
        TripCalculateRequest tripRequest = mapper.fromJson(TRIP_REQUEST_DEFAULT.load(), TripCalculateRequest.class);

        assertEquals(THE_FASTEST, tripRequest.getCalculationMode());

    }

    @Test
    void shouldDeserializeCalculationModeTheShortest() {
        TripCalculateRequest tripRequest = mapper.fromJson(TRIP_REQUEST_THE_SHORTEST.load(), TripCalculateRequest.class);

        assertEquals(THE_SHORTEST, tripRequest.getCalculationMode());

    }

    @Test
    void shouldDeserializeTripWithTurnByTurn() {
        TripCalculateRequest tripRequest = mapper.fromJson(TRIP_REQUEST_WITH_TURN_BY_TURN.load(), TripCalculateRequest.class);

        assertEquals(PT_BR.getLanguageCode(), tripRequest.getTurnByTurn().getLanguageCode());
    }
}
