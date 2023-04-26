package global.maplink.place.testUtils;

import lombok.RequiredArgsConstructor;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;

@RequiredArgsConstructor
public enum SampleFiles {
    ADDRESS("place/json/address.json"),
    LEG("place/json/leg.json"),
    LEGRESULT("place/json/legResult.json"),
    OPENHOUR("place/json/openHour.json"),
    OPENHOURTIMEDAY("place/json/openHourTimeDay.json"),
    OPENINGHOURS("place/json/openingHours.json"),
    ORIGIN("place/json/origin.json"),
    PAGERESULT("place/json/pageResult.json"),
    PLACE("place/json/place.json"),
    PLACEROUTE("place/json/placeRoute.json"),
    PLACEROUTEREQUEST("place/json/placeRouteRequest.json"),
    PLACEROUTERESPONSE("place/json/placeRouteResponse.json"),
    SOCIALCONNECTION("place/json/socialConnection.json");

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
