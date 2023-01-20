package global.maplink.domain;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.data.Offset.offset;

class MaplinkPointTest {

    public static final double SAMPLE_LAT = 57.64911;
    public static final double SAMPLE_LON = 10.40744;

    @Test
    void shouldReadAndSerializeGeohash() {
        String geohash = "u4pruydqqvj";
        MaplinkPoint point = MaplinkPoint.fromGeohash(geohash);
        assertThat(point.getLatitude()).isCloseTo(SAMPLE_LAT, offset(0.00001));
        assertThat(point.getLongitude()).isCloseTo(SAMPLE_LON, offset(0.00001));

        assertThat(point.toGeohash(11)).isEqualTo(geohash);
        assertThat(point.toGeohash()).isEqualTo(geohash.substring(0, 9));
    }

    @Test
    void shouldReadAndSerializeAsArray() {
        double[] coordinates = {SAMPLE_LAT, SAMPLE_LON};
        MaplinkPoint point = MaplinkPoint.from(coordinates);
        assertThat(point.getLatitude()).isCloseTo(SAMPLE_LAT, offset(0.00001));
        assertThat(point.getLongitude()).isCloseTo(SAMPLE_LON, offset(0.00001));

        assertThat(point.toArray()).containsExactly(coordinates);

        assertThatThrownBy(() -> MaplinkPoint.from(new double[]{SAMPLE_LAT}))
                .isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> MaplinkPoint.from(new double[]{SAMPLE_LAT, SAMPLE_LON, 15.0}))
                .isInstanceOf(IllegalArgumentException.class);
    }

}