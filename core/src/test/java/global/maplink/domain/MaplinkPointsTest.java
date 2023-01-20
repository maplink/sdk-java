package global.maplink.domain;

import org.assertj.core.data.Offset;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import static java.util.stream.IntStream.range;
import static org.assertj.core.api.Assertions.assertThat;

class MaplinkPointsTest {

    private static final String SAMPLE_POLYLINE = "pyynCfaw{GiCeC{DwD??iSnW@A";
    private static final List<String> SAMPLE_GEOHASHES = Arrays.asList(
            "6gycfmgep",
            "6gycfmupr",
            "6gycfqhus",
            "6gycfqhus",
            "6gycfqdp9",
            "6gycfqdpd"
    );
    private static final double[][] SAMPLE_POINTS = new double[][]{
            new double[]{
                    -23.56649,
                    -46.6538
            },
            new double[]{
                    -23.5658,
                    -46.65313
            },
            new double[]{
                    -23.56486,
                    -46.65221
            },
            new double[]{
                    -23.56486,
                    -46.65221
            },
            new double[]{
                    -23.56161,
                    -46.65613
            },
            new double[]{
                    -23.56162,
                    -46.65612
            }
    };

    @Test
    void shouldSerializeAsValidPolyline() {
        MaplinkPoints points = MaplinkPoints.from(SAMPLE_POINTS);

        assertThatMatchWithSample(points);

        assertThat(points.toPolyline()).isEqualTo(SAMPLE_POLYLINE);
    }

    @Test
    void shouldReadSelfWritedPolyline() {
        MaplinkPoints points = MaplinkPoints.from(randSampleLine(40));
        String polyline = points.toPolyline();
        assertThat(MaplinkPoints.fromPolyline(polyline)).isEqualTo(points);
    }


    @Test
    void shoulDeserializeFromPolyline() {
        MaplinkPoints points = MaplinkPoints.fromPolyline(SAMPLE_POLYLINE);

        assertThatMatchWithSample(points);
    }

    @Test
    void shouldReadSelfWritedGeohash() {
        MaplinkPoints points = MaplinkPoints.from(randSampleLine(50));
        List<String> geohash = points.toGeohash();
        MaplinkPoints fromGeohash = MaplinkPoints.fromGeohash(geohash);

        range(0, points.size()).forEach(i -> {
            MaplinkPoint point = points.get(i);
            MaplinkPoint geohashPoint = fromGeohash.get(i);
            assertThat(point.getLatitude()).isCloseTo(geohashPoint.getLatitude(), Offset.offset(0.0001));
            assertThat(point.getLongitude()).isCloseTo(geohashPoint.getLongitude(), Offset.offset(0.0001));
        });
    }

    @Test
    void shouldSerializeAsValidGeohash() {
        MaplinkPoints points = MaplinkPoints.from(SAMPLE_POINTS);
        List<String> geohash = points.toGeohash();
        assertThat(points.toGeohash()).isEqualTo(geohash);
    }

    @Test
    void shouldExtractAsListIteratorOrStream() {
        MaplinkPoints points = MaplinkPoints.from(SAMPLE_POINTS);
        MaplinkPoint[] samplePoints = Arrays.stream(SAMPLE_POINTS).map(MaplinkPoint::from).toArray(MaplinkPoint[]::new);
        assertThat(points.toList()).containsExactly(samplePoints);
        assertThat(points.stream()).containsExactly(samplePoints);
        Iterator<MaplinkPoint> expected = Arrays.stream(samplePoints).iterator();
        for (MaplinkPoint point : points) {
            assertThat(point).isEqualTo(expected.next());
        }
        assertThat(expected.hasNext()).isFalse();
    }


    private double[][] randSampleLine(int lenght) {
        return new Random()
                .ints(lenght, 0, SAMPLE_POINTS.length)
                .mapToObj(i -> SAMPLE_POINTS[i])
                .toArray(double[][]::new);
    }

    private void assertThatMatchWithSample(MaplinkPoints points) {
        assertThat(points).first()
                .isEqualTo(points.first())
                .isEqualTo(MaplinkPoint.from(SAMPLE_POINTS[0]));
        assertThat(points).last()
                .isEqualTo(points.last())
                .isEqualTo(MaplinkPoint.from(SAMPLE_POINTS[5]));

        range(0, SAMPLE_POINTS.length).forEach(i ->
                assertThat(points).element(i)
                        .isEqualTo(MaplinkPoint.from(SAMPLE_POINTS[i]))
        );
    }
}