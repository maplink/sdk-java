package global.maplink.geocode.ext.gmaps.config;

import org.junit.jupiter.api.Test;

import static global.maplink.geocode.ext.gmaps.config.GeocodeGMapsConfig.GOOGLE_MAPS_KEY_ENV;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class GeocodeGMapsConfigTest {

    @Test
    void mustFailWhenFetchFromEmptyEnv() {
        assertThatThrownBy(GeocodeGMapsConfig::fromEnv)
                .isInstanceOf(IllegalStateException.class)
                .message().contains(GOOGLE_MAPS_KEY_ENV);
    }

    @Test
    void mustFailWhenEmptyOrNullStringIsPassed() {
        assertThatThrownBy(()->new GeocodeGMapsConfig(null))
                .isInstanceOf(IllegalStateException.class)
                .message().contains(GOOGLE_MAPS_KEY_ENV);
        assertThatThrownBy(()->new GeocodeGMapsConfig(" "))
                .isInstanceOf(IllegalStateException.class)
                .message().contains(GOOGLE_MAPS_KEY_ENV);
    }

}