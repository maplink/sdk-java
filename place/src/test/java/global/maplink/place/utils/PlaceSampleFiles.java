package global.maplink.place.utils;

import lombok.RequiredArgsConstructor;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;

@RequiredArgsConstructor
public enum PlaceSampleFiles {
    PLACE_SP1("place.json/place_SP1.json"),
    PLACE_SP2("place.json/place_SP1.json"),
    PLACE_RJ1("place.json/place_SP1.json");


    private final String filePath;

    public byte[] load(){
        URL resource = PlaceSampleFiles.class.getClassLoader().getResource(filePath);
        try {
            assert resource != null;
            return Files.readAllBytes(Paths.get(resource.toURI()));
        } catch (IOException | URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }
}
