package global.maplink.json.codecs;

import global.maplink.MapLinkSDK;
import global.maplink.credentials.MapLinkCredentials;
import global.maplink.domain.MaplinkPoint;
import global.maplink.domain.MaplinkPoints;
import global.maplink.domain.PointsMode;
import global.maplink.http.HttpAsyncEngine;
import global.maplink.json.JacksonJsonMapperImpl;
import org.assertj.core.data.Offset;
import org.junit.jupiter.api.Test;

import java.nio.charset.StandardCharsets;

import static global.maplink.domain.PointsMode.*;
import static java.lang.String.format;
import static java.util.stream.IntStream.range;
import static net.javacrumbs.jsonunit.assertj.JsonAssertions.assertThatJson;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

class MaplinkPointsJacksonCodecTest {

    public static final String SAMPLE_POLYLINE = "pyynCfaw{GiCeC{DwD??iSnW@B";
    public static final MaplinkPoints SAMPLE_POINTS = MaplinkPoints.fromPolyline(SAMPLE_POLYLINE);
    private final JacksonJsonMapperImpl mapper = new JacksonJsonMapperImpl();

    @Test
    void shouldSerializeAsObjectByDefault() {
        String json = mapper.toJsonString(SAMPLE_POINTS);

        assertThatJson(json,
                j -> j.isArray().hasSize(6),
                j -> j.isArray().first().isEqualTo("{\"latitude\":-23.56649,\"longitude\":-46.6538}"),
                j -> j.isArray().last().isEqualTo("{\"latitude\":-23.56162,\"longitude\":-46.65615}")
        );

        MaplinkPoints reversePoints = mapper.fromJson(json.getBytes(StandardCharsets.UTF_8), MaplinkPoints.class);
        assertThat(reversePoints).isEqualTo(SAMPLE_POINTS);
    }

    @Test
    void shouldSerializeAsCoordinatesArray() {
        String json = PointsMode.runWith(ARRAY, () -> mapper.toJsonString(SAMPLE_POINTS));

        assertThatJson(json,
                j -> j.isArray().hasSize(6),
                j -> j.isArray().first().isEqualTo("[-23.56649,-46.6538]"),
                j -> j.isArray().last().isEqualTo("[-23.56162,-46.65615]")
        );

        MaplinkPoints reversePoints = mapper.fromJson(json.getBytes(StandardCharsets.UTF_8), MaplinkPoints.class);
        assertThat(reversePoints).isEqualTo(SAMPLE_POINTS);
    }

    @Test
    void shouldSerializeAsGeohash() {
        String json = PointsMode.runWith(GEOHASH, () -> mapper.toJsonString(SAMPLE_POINTS));

        assertThatJson(json,
                j -> j.isArray().hasSize(6),
                j -> j.isArray().first().isEqualTo("6gycfmgep"),
                j -> j.isArray().last().isEqualTo("6gycfqdp8")
        );

        MaplinkPoints reversePoints = mapper.fromJson(json.getBytes(StandardCharsets.UTF_8), MaplinkPoints.class);
        range(0, SAMPLE_POINTS.size()).forEach(i -> {
            MaplinkPoint point = SAMPLE_POINTS.get(i);
            MaplinkPoint geohashPoint = reversePoints.get(i);
            assertThat(point.getLatitude()).isCloseTo(geohashPoint.getLatitude(), Offset.offset(0.0001));
            assertThat(point.getLongitude()).isCloseTo(geohashPoint.getLongitude(), Offset.offset(0.0001));
        });
    }

    @Test
    void shouldSerializeAsPolyline() {
        String json = PointsMode.runWith(POLYLINE, () -> mapper.toJsonString(SAMPLE_POINTS));

        assertThatJson(json).isString().isEqualTo(SAMPLE_POLYLINE);

        MaplinkPoints reversePoints = mapper.fromJson(json.getBytes(StandardCharsets.UTF_8), MaplinkPoints.class);
        assertThat(reversePoints).isEqualTo(SAMPLE_POINTS);
    }

    @Test
    void shouldSerializeAsSimple() {
        String json = PointsMode.runWith(SIMPLE, () -> mapper.toJsonString(SAMPLE_POINTS));

        assertThatJson(json,
                j -> j.isArray().hasSize(6),
                j -> j.isArray().first().isEqualTo("{\"point\":\"-23.5664900,-46.653800\"}"),
                j -> j.isArray().last().isEqualTo("{\"point\":\"-23.5616200,-46.656150\"}")
        );
    }

    @Test
    void shouldDeserializeFromMultipleTypes() {
        String json = format("[%s,%s,%s]",
                "\"6gycfmgep\"",
                "[-23.56649,-46.6538]",
                "{\"latitude\":-23.56649,\"longitude\":-46.6538}"
        );

        MaplinkPoints points = mapper.fromJson(json.getBytes(StandardCharsets.UTF_8), MaplinkPoints.class);
        assertThat(points).hasSize(3);
    }

    @Test
    void shouldRespectExternalSupplier() {
        try {
            PointsMode.setExternalSupplier(() -> POLYLINE);
            String json = mapper.toJsonString(SAMPLE_POINTS);

            assertThatJson(json).isString().isEqualTo(SAMPLE_POLYLINE);
        } finally {
            PointsMode.setExternalSupplier(() -> null);
        }
    }

    @Test
    void shouldTakeFromSDKExternalSupplier() {
        try {
            MapLinkSDK.resetConfiguration();
            MapLinkSDK.configure()
                    .with(mock(HttpAsyncEngine.class))
                    .with(mock(MapLinkCredentials.class))
                    .with(GEOHASH)
                    .initialize();

            String json = mapper.toJsonString(SAMPLE_POINTS);

            assertThatJson(json,
                    j -> j.isArray().hasSize(6),
                    j -> j.isArray().first().isEqualTo("6gycfmgep"),
                    j -> j.isArray().last().isEqualTo("6gycfqdp8")
            );
        } finally {
            MapLinkSDK.resetConfiguration();
        }
    }

}