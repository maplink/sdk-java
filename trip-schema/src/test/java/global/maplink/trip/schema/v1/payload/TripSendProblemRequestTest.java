package global.maplink.trip.schema.v1.payload;

import global.maplink.json.JsonMapper;
import global.maplink.trip.schema.v1.exception.TripViolation;
import global.maplink.trip.schema.v2.problem.SitePoint;
import global.maplink.trip.testUtils.V1SampleFiles;
import global.maplink.validations.ValidationException;
import global.maplink.validations.ValidationViolation;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Stream;

import static global.maplink.trip.schema.v1.payload.AvoidanceType.*;
import static global.maplink.trip.schema.v1.payload.LoadType.*;
import static global.maplink.trip.schema.v1.payload.RoadType.*;
import static global.maplink.trip.schema.v2.problem.CalculationMode.THE_FASTEST;
import static global.maplink.trip.testUtils.V1SampleFiles.*;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.assertj.core.api.Assertions.assertThat;

class TripSendProblemRequestTest {
    private final JsonMapper mapper = JsonMapper.loadDefault();

    @Test
    void shouldDeserialize() {
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

    @ParameterizedTest
    @MethodSource("invalidRequestProvider")
    void shouldRejectInvalidRequest(V1SampleFiles sampleFile, String expectedMessage) {
        TripSendProblemRequest request = mapper.fromJson(sampleFile.load(), TripSendProblemRequest.class);
        List<ValidationViolation> violations = request.validate();

        assertThatThrownBy(request::throwIfInvalid).isInstanceOf(ValidationException.class);
        assertThat(violations).hasSize(1);
        assertThat(violations.get(0)).isInstanceOf(TripViolation.class);
        assertThat(violations.get(0).getMessage()).isEqualTo(expectedMessage);
    }

    @ParameterizedTest
    @MethodSource("invalidRequestProvider")
    void shouldSerializeViolationMessageCorrectly(V1SampleFiles sampleFile, String expectedMessage) {
        TripSendProblemRequest request = mapper.fromJson(sampleFile.load(), TripSendProblemRequest.class);
        String serialized = mapper.toJsonString(request.validate());
        assertThat(serialized).contains("\"message\":\"" + expectedMessage + "\"");
    }

    static Stream<Arguments> invalidRequestProvider() {
        return Stream.of(
                Arguments.of(TRIP_SEND_PROBLEM_WITH_NULL_PROFILE_NAME, "Field profileName should not be empty"),
                Arguments.of(TRIP_SEND_PROBLEM_WITH_CALLBACK_WITHOUT_URL, "Callback parameters must have an url"),
                Arguments.of(TRIP_SEND_PROBLEM_WITH_TOLL_WITHOUT_VEHICLE_TYPE, "Toll parameters must have a vehicleType"),
                Arguments.of(TRIP_SEND_PROBLEM_WITH_CROSSED_BORDERS_WITHOUT_LEVEL, "CrossedBorders parameters must have a level"),
                Arguments.of(TRIP_SEND_PROBLEM_WITH_INCOMPLETE_SPEED_PREFERENCES, "SpeedPreferences should contain one element for each roadType"),
                Arguments.of(TRIP_SEND_PROBLEM_WITH_NULL_TURN_BY_TURN_LANGUAGE, "TurnByTurn language cannot be null")
        );
    }
}
