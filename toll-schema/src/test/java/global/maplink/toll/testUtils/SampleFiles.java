package global.maplink.toll.testUtils;

import lombok.RequiredArgsConstructor;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;

@RequiredArgsConstructor
public enum SampleFiles {
    CALCULATION_DETAIL("toll/json/calculationDetail.json"),
    COORDINATES("toll/json/coordinates.json"),
    LEG_RESULT("toll/json/legResult.json"),
    STATE("toll/json/state.json"),
    TOLL_CONDITION("toll/json/tollCondition.json"),
    TOLL_SERVICE_TYPE("toll/json/tollServiceType.json"),
    CALCULATION_REQUEST("toll/json/calculationRequest.json"),
    CALCULATION_REQUEST_CONECTCAR("toll/json/calculationRequest_Conectcar.json"),
    CALCULATION_RESULT("toll/json/calculationResult.json"),
    CALCULATION_REQUEST_CONDITIONS("toll/json/calculationConditions.json"),
    CALCULATION_DATE_DEFAULT("toll/json/calculationDate.json"),
    CALCULATION_CONDITIONS_DEFAULT("toll/json/calculationConditionsDefault.json"),
    CALCULATION_DEFAULT("toll/json/calculationDefault.json");

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
