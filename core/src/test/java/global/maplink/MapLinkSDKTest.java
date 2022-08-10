package global.maplink;

import global.maplink.credentials.MapLinkCredentials;
import global.maplink.http.MockHttpAsyncEngine;
import global.maplink.json.MockJsonMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class MapLinkSDKTest {

    public static final String CLIENT_ID = "clientId";
    public static final String CLIENT_SECRET = "secret";

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
                .with(MapLinkCredentials.ofKey(CLIENT_ID, CLIENT_SECRET))
                .with(new MockJsonMapper())
                .with(new MockHttpAsyncEngine())
                .initialize();

        assertThat(MapLinkSDK.getInstance()).isNotNull().isSameAs(MapLinkSDK.getInstance());
    }

}
