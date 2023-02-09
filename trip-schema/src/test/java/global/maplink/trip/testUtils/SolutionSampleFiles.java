package global.maplink.trip.testUtils;

import lombok.RequiredArgsConstructor;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;

@RequiredArgsConstructor
public enum SolutionSampleFiles {
    COORDINATES("trip/v2/solution/json/coordinates.json"),
    CROSSED_BORDER_RESPONSE("trip/v2/solution/json/crossedBorderResponse.json"),
    SOLUTION_LEG("trip/v2/solution/json/solutionLeg.json"),
    TRIP_RESPONSE("trip/v2/solution/json/tripResponse.json");

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
