package global.maplink.trip.testUtils;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;

@Getter
@RequiredArgsConstructor
public enum V1SampleFiles {
    CALLBACK("trip/v1/callback.json"),
    SPEED_PREFERENCE("trip/v1/speedPreference.json"),
    VALID_VEHICLE_SPECIFICATION("trip/v1/validVehicleSpecification.json"),
    INVALID_VEHICLE_SPECIFICATION("trip/v1/invalidVehicleSpecification.json"),
    VALID_TRIP_PROBLEM("trip/v1/validTripProblem.json"),
    TRIP_SEND_PROBLEM_WITH_NULL_PROFILE_NAME("trip/v1/tripSendProblemWithNullProfileName.json"),
    TRIP_SEND_PROBLEM_WITH_CALLBACK_WITHOUT_URL("trip/v1/tripSendProblemWithCallbackWithoutUrl.json"),
    TRIP_SEND_PROBLEM_WITH_TOLL_WITHOUT_VEHICLE_TYPE("trip/v1/tripSendProblemWithTollWithoutVehicleType.json"),
    TRIP_SEND_PROBLEM_WITH_CROSSED_BORDERS_WITHOUT_LEVEL("trip/v1/tripSendProblemWithCrossedBordersWithoutLevel.json"),
    TRIP_SEND_PROBLEM_WITH_INCOMPLETE_SPEED_PREFERENCES("trip/v1/tripSendProblemWithIncompleteSpeedPreferences.json"),
    TRIP_SEND_PROBLEM_WITH_NULL_TURN_BY_TURN_LANGUAGE("trip/v1/tripSendProblemWithNullTurnByTurnLanguage.json"),
    TRIP_RESPONSE("trip/v1/tripResponse.json");

    private final String filePath;

    public byte[] load(){
        URL resource = ProblemSampleFiles.class.getClassLoader().getResource(filePath);
        try {
            assert resource != null;
            return Files.readAllBytes(Paths.get(resource.toURI()));
        } catch (IOException | URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }
}
