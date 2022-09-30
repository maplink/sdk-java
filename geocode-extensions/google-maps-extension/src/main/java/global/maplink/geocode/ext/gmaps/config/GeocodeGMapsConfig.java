package global.maplink.geocode.ext.gmaps.config;

import lombok.Data;

import static global.maplink.geocode.ext.gmaps.config.GeocodeGmapsSwitchStrategy.defaultStrategy;
import static java.lang.String.format;
import static java.lang.System.getenv;
import static java.util.Optional.ofNullable;

@Data
public class GeocodeGMapsConfig {

    public static final String GOOGLE_MAPS_KEY_ENV = "GOOGLE_MAPS_KEY";
    public static final String ERROR_MESSAGE_TEMPLATE = "Environment Variable %s could not be found";

    private final String apiKey;

    private final GeocodeGmapsSwitchStrategy switchStrategy;

    public GeocodeGMapsConfig(String apiKey) {
        this(apiKey, defaultStrategy());
    }

    public GeocodeGMapsConfig(String key, GeocodeGmapsSwitchStrategy strategy) {
        apiKey = ofNullable(key)
                .map(String::trim)
                .filter(s -> !s.isEmpty())
                .orElseThrow(() -> new IllegalStateException(format(ERROR_MESSAGE_TEMPLATE, GOOGLE_MAPS_KEY_ENV)));
        switchStrategy = strategy;
    }

    public static GeocodeGMapsConfig fromEnv() {
        return new GeocodeGMapsConfig(getenv(GOOGLE_MAPS_KEY_ENV));
    }
}
