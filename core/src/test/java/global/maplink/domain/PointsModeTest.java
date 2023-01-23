package global.maplink.domain;

import global.maplink.MapLinkSDK;
import global.maplink.credentials.MapLinkCredentials;
import global.maplink.http.HttpAsyncEngine;
import global.maplink.json.JsonMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import static global.maplink.domain.PointsMode.GEOHASH;
import static global.maplink.domain.PointsMode.POLYLINE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

class PointsModeTest {
    @AfterEach
    void cleanup() {
        PointsMode.setExternalSupplier(() -> null);
        MapLinkSDK.resetConfiguration();
    }

    @Test
    void currentShouldBeDefault() {
        assertThat(PointsMode.current()).isEqualTo(PointsMode.loadDefault());
    }

    @Test
    void runWithSouldOverrideCurrentInContext() {
        assertThat(PointsMode.current()).isEqualTo(PointsMode.loadDefault());

        PointsMode current = PointsMode.runWith(POLYLINE, PointsMode::current);
        assertThat(current).isEqualTo(POLYLINE);
        assertThat(current).isNotEqualTo(PointsMode.loadDefault());

        assertThat(PointsMode.current()).isEqualTo(PointsMode.loadDefault());
    }

    @Test
    void externalSupplierShouldOverrideDefaultAndSDK() {
        assertThat(PointsMode.current()).isEqualTo(PointsMode.loadDefault());

        PointsMode.setExternalSupplier(() -> GEOHASH);
        assertThat(PointsMode.current()).isEqualTo(GEOHASH);
        assertThat(PointsMode.current()).isNotEqualTo(PointsMode.loadDefault());

        configureSDK(POLYLINE);
        assertThat(PointsMode.current()).isEqualTo(GEOHASH);
        assertThat(PointsMode.current()).isNotEqualTo(PointsMode.loadDefault());
    }


    @Test
    void shouldTakeFromSDK() {
        assertThat(PointsMode.current()).isEqualTo(PointsMode.loadDefault());
        configureSDK(GEOHASH);
        assertThat(PointsMode.current()).isEqualTo(GEOHASH);
        assertThat(PointsMode.current()).isNotEqualTo(PointsMode.loadDefault());
    }

    private void configureSDK(PointsMode pointsMode) {
        MapLinkSDK.configure()
                .with(mock(HttpAsyncEngine.class))
                .with(mock(JsonMapper.class))
                .with(mock(MapLinkCredentials.class))
                .with(pointsMode)
                .initialize();
    }

}