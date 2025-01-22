package global.maplink.domain;

import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.lang.Math.floor;
import static java.lang.Math.max;
import static java.util.stream.IntStream.range;

@RequiredArgsConstructor
@EqualsAndHashCode
@ToString
public class MaplinkPoints implements Iterable<MaplinkPoint> {

    public static final MaplinkPoints EMPTY = new MaplinkPoints(new MaplinkPoint[0]);

    private static final double DEFAULT_POLYLINE_PRECISION = 1e5;

    private final MaplinkPoint[] data;

    public int size() {
        return data.length;
    }

    public MaplinkPoint first() {
        if (data.length == 0) {
            return null;
        }
        return data[0];
    }

    public MaplinkPoint get(int pos) {
        if (pos >= data.length) {
            return null;
        }
        return data[pos];
    }

    public MaplinkPoint last() {
        if (data.length == 0) {
            return null;
        }
        return data[data.length - 1];
    }

    public String toPolyline() {
        StringBuilder sb = new StringBuilder(max(20, data.length * 3));
        int prevLat = 0;
        int prevLon = 0;
        for (MaplinkPoint point : data) {
            int num = (int) floor(point.getLatitude() * DEFAULT_POLYLINE_PRECISION);
            encodePolylineNumber(sb, num - prevLat);
            prevLat = num;
            num = (int) floor(point.getLongitude() * DEFAULT_POLYLINE_PRECISION);
            encodePolylineNumber(sb, num - prevLon);
            prevLon = num;
        }
        return sb.toString();
    }

    private static void encodePolylineNumber(StringBuilder sb, int num) {
        num = num << 1;
        if (num < 0) {
            num = ~num;
        }
        while (num >= 0x20) {
            int nextValue = (0x20 | (num & 0x1f)) + 63;
            sb.append((char) (nextValue));
            num >>= 5;
        }
        num += 63;
        sb.append((char) (num));
    }

    public List<String> toGeohash() {
        return toList(MaplinkPoint::toGeohash);
    }

    public List<String> toGeohash(int size) {
        return toList(point -> point.toGeohash(size));
    }

    public <T> List<T> toList(Function<MaplinkPoint, T> transform) {
        return stream()
                .map(transform)
                .collect(Collectors.toList());
    }

    public List<MaplinkPoint> toList() {
        return Arrays.asList(data);
    }

    public Stream<MaplinkPoint> stream() {
        return Arrays.stream(data);
    }

    @Override
    public Iterator<MaplinkPoint> iterator() {
        return stream().iterator();
    }

    @Override
    public Spliterator<MaplinkPoint> spliterator() {
        return Arrays.spliterator(data);
    }

    public static MaplinkPoints from(double... points) {
        if (points.length == 0) {
            return EMPTY;
        }
        if (points.length % 2 != 0) {
            throw new IllegalArgumentException("MaplinkPoints.from requires coordinates in pairs");
        }
        return new MaplinkPoints(
                range(0, points.length / 2)
                        .map(i -> i * 2)
                        .mapToObj(v -> new MaplinkPoint(points[v], points[v + 1]))
                        .toArray(MaplinkPoint[]::new)
        );
    }

    public static MaplinkPoints from(double[][] points) {
        return new MaplinkPoints(
                Arrays.stream(points)
                        .map(MaplinkPoint::from)
                        .toArray(MaplinkPoint[]::new)
        );
    }

    public static MaplinkPoints fromGeohash(List<String> geohash) {
        return new MaplinkPoints(
                geohash.stream()
                        .map(MaplinkPoint::fromGeohash)
                        .toArray(MaplinkPoint[]::new)
        );
    }

    public static MaplinkPoints fromPolyline(String encoded) {
        List<MaplinkPoint> points = new LinkedList<>();
        int index = 0;
        int len = encoded.length();
        int[] coordinates = new int[]{0, 0};

        while (index < len) {
            int b;
            int[] delta = new int[]{0, 0};
            for (int i = 0; i < 2; i++) {
                int shift = 0;
                int result = 0;
                do {
                    if(index >= len){
                        break;
                    }
                    b = encoded.charAt(index++) - 63;
                    result |= (b & 0x1f) << shift;
                    shift += 5;
                } while (b >= 0x20);
                delta[i] = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
                coordinates[i] += delta[i];
            }

            points.add(new MaplinkPoint(
                    (double) coordinates[0] / DEFAULT_POLYLINE_PRECISION,
                    (double) coordinates[1] / DEFAULT_POLYLINE_PRECISION
            ));
        }
        return new MaplinkPoints(points.stream().toArray(MaplinkPoint[]::new));
    }

}
