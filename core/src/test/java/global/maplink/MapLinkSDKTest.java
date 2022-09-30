package global.maplink;

import global.maplink.credentials.MapLinkCredentials;
import global.maplink.extensions.SdkExtension;
import global.maplink.extensions.SdkExtensionCatalog;
import global.maplink.http.MockHttpAsyncEngine;
import global.maplink.json.MockJsonMapper;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Arrays.asList;
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
    public void mustFailOnConfigureAlreadyConfigured() {
        Runnable configure = () ->
                MapLinkSDK.configure()
                        .with(MapLinkCredentials.ofKey(CLIENT_ID, CLIENT_SECRET))
                        .with(new MockJsonMapper())
                        .with(new MockHttpAsyncEngine())
                        .initialize();
        configure.run();
        assertThatThrownBy(configure::run)
                .isInstanceOf(IllegalStateException.class);
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

    @Test
    public void mustAcceptExtensionsAndExtensionsCatalogs() {
        MapLinkSDK.configure()
                .with(MapLinkCredentials.ofKey(CLIENT_ID, CLIENT_SECRET))
                .with(new MockJsonMapper())
                .with(new MockHttpAsyncEngine())
                .with(new SampleExtension("SingleExt"))
                .with(new SampleCatalog("CatalogExt-"))
                .initialize();
        MapLinkSDK instance = MapLinkSDK.getInstance();
        List<String> names = instance.getExtensions().stream().map(SdkExtension::getName).collect(Collectors.toList());

        assertThat(names).hasSize(3).containsExactlyInAnyOrder(
                "SingleExt",
                "CatalogExt-1",
                "CatalogExt-2"
        );
    }

    @RequiredArgsConstructor
    @Getter
    private static class SampleExtension implements SdkExtension {
        private final String name;
    }

    @RequiredArgsConstructor
    @Getter
    private static class SampleCatalog implements SdkExtensionCatalog {
        private final String name;

        @Override
        public Collection<SdkExtension> getAll() {
            return asList(
                    new SampleExtension(name + 1),
                    new SampleExtension(name + 2)
            );
        }
    }

}
