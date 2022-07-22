package global.maplink;

import global.maplink.credentials.MapLinkCredentials;
import global.maplink.http.MockHttpAsyncEngine;
import global.maplink.json.MockJsonMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class MapLinkSDKTest {

    public static final String EMPTY_TOKEN = "EMPTY";

    @BeforeEach
    public void cleanupConfig() {
        MapLinkSDK.resetConfiguration();
    }

    @Test
    public void mustFailOnInitializeWithoutConfigure() {
        assertThatThrownBy(MapLinkSDK::getInstance)
                .isInstanceOf(MapLinkNotConfiguredException.class);
    }

    @Test
    public void mustCreateGlobalInstanceAfterConfiguration() {
        MapLinkSDK.configure()
                .with(MapLinkCredentials.ofToken(EMPTY_TOKEN))
                .with(new MockJsonMapper())
                .with(new MockHttpAsyncEngine())
                .initialize();

        assertThat(MapLinkSDK.getInstance()).isNotNull().isSameAs(MapLinkSDK.getInstance());
    }

}
