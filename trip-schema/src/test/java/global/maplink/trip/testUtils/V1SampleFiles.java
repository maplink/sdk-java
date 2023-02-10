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
    INVALID_TRIP_PROBLEM("trip/v1/invalidTripProblem.json"),
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
