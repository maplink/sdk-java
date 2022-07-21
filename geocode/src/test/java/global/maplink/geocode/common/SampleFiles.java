package global.maplink.geocode.common;

import lombok.RequiredArgsConstructor;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.file.Files;
import java.nio.file.Paths;

@RequiredArgsConstructor

public enum SampleFiles {

    SUGGESTIONS_RESPONSE("geocode/json/sampleSuggestionsResponse.json");

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
