package global.maplink.geocode.ext.gmaps;

import org.junit.jupiter.api.Test;

import static global.maplink.geocode.ext.gmaps.GeocodeGMapsConfig.GOOGLE_MAPS_KEY_ENV;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class GeocodeGMapsConfigTest {

    @Test
    void mustFailWhenFetchFromEmptyEnv() {
        assertThatThrownBy(GeocodeGMapsConfig::fromEnv)
                .isInstanceOf(IllegalStateException.class)
                .message().contains(GOOGLE_MAPS_KEY_ENV);
    }

}