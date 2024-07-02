package global.maplink.planning.testUtils;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;

public interface TestFiles {

    String getFilePath();

    default byte[] load() {
        URL resource = SampleFiles.class.getClassLoader().getResource(getFilePath());
        try {
            assert resource != null;
            return Files.readAllBytes(Paths.get(resource.toURI()));
        } catch (IOException | URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }
}
