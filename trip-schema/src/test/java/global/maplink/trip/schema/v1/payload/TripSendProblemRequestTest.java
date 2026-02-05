package global.maplink.trip.schema.v1.payload;

import global.maplink.json.JsonMapper;
import global.maplink.trip.schema.v2.problem.SitePoint;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static global.maplink.trip.schema.v1.exception.TripErrorType.*;
import static global.maplink.trip.schema.v1.payload.AvoidanceType.*;
import static global.maplink.trip.schema.v1.payload.LoadType.*;
import static global.maplink.trip.schema.v1.payload.RoadType.*;
import static global.maplink.trip.schema.v1.payload.RoadType.FERRY;
import static global.maplink.trip.schema.v1.payload.RoadType.HIGHWAY;
import static global.maplink.trip.schema.v1.payload.RoadType.LOCAL_ROAD;
import static global.maplink.trip.schema.v1.payload.RoadType.PENALIZED_LOCAL_ROAD;
import static global.maplink.trip.schema.v1.payload.RoadType.PENALIZED_SECONDARY_ROAD;
import static global.maplink.trip.schema.v1.payload.RoadType.SECONDARY_ROAD;
import static global.maplink.trip.schema.v2.problem.CalculationMode.THE_FASTEST;
import static global.maplink.trip.testUtils.V1SampleFiles.INVALID_TRIP_PROBLEM;
import static global.maplink.trip.testUtils.V1SampleFiles.VALID_TRIP_PROBLEM;
import static org.assertj.core.api.Assertions.assertThat;

class TripSendProblemRequestTest {
    private final JsonMapper mapper = JsonMapper.loadDefault();

    @Test
    void shouldDeserialize(){
        TripSendProblemRequest tripProblem = mapper.fromJson(VALID_TRIP_PROBLEM.load(), TripSendProblemRequest.class);

        assertThat(tripProblem).isNotNull();
        assertThat(tripProblem.getClientId()).isEqualTo("DEFAULT_CLIENT_ID");
        assertThat(tripProblem.getProfileName()).isEqualTo("MAPLINKBR");
        assertThat(tripProblem.getCalculationMode()).isEqualTo(THE_FASTEST);

        assertThat(tripProblem.getPoints()).isNotNull();
        assertThat(tripProblem.getPoints().size()).isEqualTo(2);
        assertThat(tripProblem.getPoints())
                .containsExactly(
                        SitePoint.builder()
                                .siteId("Start")
                                .latitude(BigDecimal.valueOf(-13.8169976))
                                .longitude(BigDecimal.valueOf(-56.0884274))
                                .build(),
                        SitePoint.builder()
                                .siteId("End")
                                .latitude(BigDecimal.valueOf(-12.5407237)).
                                longitude(BigDecimal.valueOf(-55.7226752))
                                .build()
                );

        assertThat(tripProblem.getSpeedPreferences())
                .containsExactly(
                    SpeedPreference.builder()
                        .roadType(HIGHWAY)
                        .speed(90)
                        .speedAtToll(50).build(),
                    SpeedPreference.builder()
                        .roadType(FERRY)
                        .speed(90)
                        .speedAtToll(50).build(),
                    SpeedPreference.builder()
                        .roadType(PENALIZED_LOCAL_ROAD)
                        .speed(90)
                        .speedAtToll(50).build(),
                    SpeedPreference.builder()
                        .roadType(LOCAL_ROAD)
                        .speed(90)
                        .speedAtToll(50).build(),
                    SpeedPreference.builder()
                        .roadType(PENALIZED_SECONDARY_ROAD)
                        .speed(90)
                        .speedAtToll(50).build(),
                    SpeedPreference.builder()
                        .roadType(SECONDARY_ROAD)
                        .speed(90)
                        .speedAtToll(50).build(),
                    SpeedPreference.builder()
                        .roadType(PENALIZED_MAIN_ROAD)
                        .speed(90)
                        .speedAtToll(50).build(),
                    SpeedPreference.builder()
                        .roadType(MAIN_ROAD)
                        .speed(90)
                        .speedAtToll(50).build(),
                    SpeedPreference.builder()
                        .roadType(EXPRESSWAY)
                        .speed(90)
                        .speedAtToll(50).build()
                );

        assertThat(tripProblem.getVehicleSpecification())
                .isEqualTo(
                        VehicleSpecification.builder()
                                .maxWeight(10000D)
                                .maxWeightPerAxle(2000D)
                                .maxLength(40D)
                                .maxLengthBetweenAxles(5D)
                                .maxWidth(50D)
                                .maxHeight(5D)
                                .maxWeightForExplodingMaterials(30D)
                                .maxWeightForPollutingMaterials(30D)
                                .maxWeightForDangerousMaterials(30D)
                                .loadType(BUILDING_PRODUCTS)
                                .loadType(HEATH_WASTES)
                                .loadType(SAND_GRAVELS)
                                .build()
                );

        assertThat(tripProblem.getAvoidanceTypes()).containsExactlyInAnyOrder(TUNNELS, BRIDGES, FERRIES, FRONTIERS,
                TOLL_ROADS, TOLL_GATES, UNPAVED);

        assertThat(tripProblem.getCallback())
                .isEqualTo(
                        Callback.builder()
                                .url("http://localhost")
                                .user("user")
                                .password("password")
                                .build()
                );
    }

    @Test
    void shouldValidateReturnEmptyErrorArray(){
        TripSendProblemRequest tripProblem = mapper.fromJson(VALID_TRIP_PROBLEM.load(), TripSendProblemRequest.class);
        assertThat(tripProblem.validate()).isEmpty();
    }

    @Test
    void shouldValidateReturnNotEmptyErrorArray(){
        TripSendProblemRequest tripProblem = mapper.fromJson(INVALID_TRIP_PROBLEM.load(), TripSendProblemRequest.class);
        assertThat(tripProblem.validate()).isNotEmpty();
        assertThat(tripProblem.validate()).containsExactlyInAnyOrder(
                PROFILE_NAME_EMPTY, CALLBACK_DOES_NOT_HAVE_URL, ROAD_TYPE_ELEMENTS_EMPTY, TURN_BY_TURN_LANGUAGE_NOT_FOUND
        );
    }
}
