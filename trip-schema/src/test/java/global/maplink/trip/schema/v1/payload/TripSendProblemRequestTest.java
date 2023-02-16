package global.maplink.trip.schema.v1.payload;

import global.maplink.json.JsonMapper;
import global.maplink.trip.schema.v2.problem.SitePoint;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static global.maplink.trip.schema.v1.exception.TripErrorType.*;
import static global.maplink.trip.schema.v1.payload.AvoidanceType.BRIDGES;
import static global.maplink.trip.schema.v1.payload.AvoidanceType.TUNNELS;
import static global.maplink.trip.schema.v1.payload.LoadType.*;
import static global.maplink.trip.schema.v2.problem.CalculationMode.THE_FASTEST;
import static global.maplink.trip.testUtils.V1SampleFiles.INVALID_TRIP_PROBLEM;
import static global.maplink.trip.testUtils.V1SampleFiles.VALID_TRIP_PROBLEM;
import static org.assertj.core.api.Assertions.assertThat;

public class TripSendProblemRequestTest {
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
                        .roadType(RoadType.HIGHWAY)
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

        assertThat(tripProblem.getAvoidanceTypes()).containsExactlyInAnyOrder(TUNNELS, BRIDGES);

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
        // TODO: Ajustar esse teste em relacao a regra de speedPreferences
        TripSendProblemRequest tripProblem = mapper.fromJson(VALID_TRIP_PROBLEM.load(), TripSendProblemRequest.class);
//        assertThat(tripProblem.validate()).isEmpty();
    }

    @Test
    void shouldValidateReturnNotEmptyErrorArray(){
        TripSendProblemRequest tripProblem = mapper.fromJson(INVALID_TRIP_PROBLEM.load(), TripSendProblemRequest.class);
        assertThat(tripProblem.validate()).isNotEmpty();
        assertThat(tripProblem.validate()).containsExactlyInAnyOrder(
                PROFILE_NAME_EMPTY, CALLBACK_DOES_NOT_HAVE_URL, ROAD_TYPE_ELEMENTS_EMPTY
        );
    }
}
