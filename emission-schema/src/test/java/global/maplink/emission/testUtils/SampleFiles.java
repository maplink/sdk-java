package global.maplink.emission.testUtils;

import lombok.RequiredArgsConstructor;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;

@RequiredArgsConstructor
public enum SampleFiles {

    EMISSION_REQUEST("emission/json/emissionRequest.json"),
    EMISSION_REQUEST_WITH_FRACTIONS("emission/json/emissionRequestWithFractions.json"),
    EMISSION_REQUEST_WITH_AVERAGE_CONSUMPTION("emission/json/emissionRequestWithAverageConsumption.json"),
    EMISSION_REQUEST_WITH_ZERO_TOTAL_DISTANCE("emission/json/emissionRequestWithZeroTotalDistance.json"),
    EMISSION_REQUEST_WITH_NULL_FUEL_TYPE("emission/json/emissionRequestWithNullFuelType.json"),
    EMISSION_REQUEST_WITH_NULL_TOTAL_DISTANCE("emission/json/emissionRequestWithNullTotalDistance.json"),
    EMISSION_REQUEST_WITH_NEGATIVE_TOTAL_DISTANCE("emission/json/emissionRequestWithNegativeTotalDistance.json"),
    EMISSION_REQUEST_WITH_NULL_AUTONOMY("emission/json/emissionRequestWithNullAutonomy.json"),
    EMISSION_REQUEST_WITH_WRONG_PERCENTAGES("emission/json/emissionRequestWithWrongPercentages.json"),
    EMISSION_RESPONSE("emission/json/emissionResponse.json"),
    EMISSION_RESPONSE_WITH_FRACTIONS("emission/json/emissionResponseWithFractions.json");

    private final String filePath;

    public byte[] load() {
        URL resource = SampleFiles.class.getClassLoader().getResource(filePath);
        try {
            assert resource != null;
            return Files.readAllBytes(Paths.get(resource.toURI()));
        } catch (IOException | URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }
}
