package global.maplink.geocode.sync;

import global.maplink.MapLinkSDK;
import global.maplink.credentials.MapLinkCredentials;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static global.maplink.geocode.common.Defaults.DEFAULT_TOKEN;
import static org.assertj.core.api.Assertions.assertThat;

public class GeocodeSyncApiTest {

    @BeforeEach
    public void initializeSDK() {
        MapLinkSDK
                .configure()
                .with(MapLinkCredentials.ofToken(DEFAULT_TOKEN))
                .initialize();
    }

    @AfterEach
    public void cleanupSDK() {
        MapLinkSDK.resetConfiguration();
    }

    @Test
    public void mustBeInstantiableWithGetInstance() {
        GeocodeSyncAPI instance = GeocodeSyncAPI.getInstance();
        assertThat(instance).isNotNull();
    }
}
