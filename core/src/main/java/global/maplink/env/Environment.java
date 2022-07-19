package global.maplink.env;

import java.net.MalformedURLException;
import java.net.URL;

public interface Environment {

    String getHost();

    default URL withService(String servicePath) {
        String parsed = servicePath.trim();
        if (!parsed.startsWith("/")) {
            parsed = "/" + parsed;
        }
        try {
            return new URL(getHost() + parsed);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }

    static Environment loadDefault() {
        return EnvironmentCatalog.PRODUCTION;
    }
}
