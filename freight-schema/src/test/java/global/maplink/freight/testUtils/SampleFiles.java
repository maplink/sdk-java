package global.maplink.freight.testUtils;

import lombok.RequiredArgsConstructor;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;

@RequiredArgsConstructor
public enum SampleFiles {
    ADDITIONAL_COSTS("freight/json/additionalCosts.json"),
    FREIGHT_CALCULATION_REQUEST("freight/json/freightCalculationRequest.json"),
    FREIGHT_CALCULATION_RESPONSE("freight/json/freightCalculationResponse.json"),
    FREIGHT_CALCULATION_RESULT("freight/json/freightCalculationResult.json");

    private final String filePath;

    public byte[] load(){
        URL resource = SampleFiles.class.getClassLoader().getResource(filePath);
        try {
            assert resource != null;
            return Files.readAllBytes(Paths.get(resource.toURI()));
        } catch (IOException | URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }
}
