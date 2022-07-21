package global.maplink.geocode.sync;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class GeocodeSyncApiTest {

    @Test
    public void mustBeInstantiableWithGetInstance() {
        GeocodeSyncAPI instance = GeocodeSyncAPI.getInstance();
        assertThat(instance).isNotNull();
    }
}
