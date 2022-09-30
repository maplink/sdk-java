package global.maplink.geocode.ext.gmaps;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import static java.lang.String.format;
import static java.lang.System.getenv;
import static java.util.Optional.ofNullable;

@RequiredArgsConstructor
@Data
public class GeocodeGMapsConfig {

    public static final String GOOGLE_MAPS_KEY_ENV = "GOOGLE_MAPS_KEY";
    public static final String ERROR_MESSAGE_TEMPLATE = "Environment Variable %s could not be found";

    private final String apiKey;

    public static GeocodeGMapsConfig fromEnv() {
        return ofNullable(getenv(GOOGLE_MAPS_KEY_ENV))
                .map(String::trim)
                .filter(s -> !s.isEmpty())
                .map(GeocodeGMapsConfig::new)
                .orElseThrow(() -> new IllegalStateException(format(ERROR_MESSAGE_TEMPLATE, GOOGLE_MAPS_KEY_ENV)));
    }
}
