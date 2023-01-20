package global.maplink.domain;

import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static global.maplink.helpers.PolylineHelper.encodePolyline;

@RequiredArgsConstructor
@EqualsAndHashCode
@ToString
public class MaplinkPoints implements Iterable<MaplinkPoint> {

    private static final int DEFAULT_POLYLINE_PRECISION = 15;

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
        return data[pos];
    }

    public MaplinkPoint last() {
        if (data.length == 0) {
            return null;
        }
        return data[data.length - 1];
    }

    public String toPolyline() {
        return encodePolyline(
                stream()
                        .map(MaplinkPoint::toArray)
                        .toArray(double[][]::new),
                DEFAULT_POLYLINE_PRECISION
        );
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
        int lat = 0;
        int lng = 0;
        while (index < len) {
            // latitude
            int b;
            int shift = 0;
            int result = 0;
            do {
                b = encoded.charAt(index++) - 63;
                result |= (b & 0x1f) << shift;
                shift += 5;
            } while (b >= 0x20);
            int deltaLatitude = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
            lat += deltaLatitude;

            // longitude
            shift = 0;
            result = 0;
            do {
                b = encoded.charAt(index++) - 63;
                result |= (b & 0x1f) << shift;
                shift += 5;
            } while (b >= 0x20);
            int deltaLongitude = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
            lng += deltaLongitude;

            points.add(new MaplinkPoint(
                    (double) lat / 1e5,
                    (double) lng / 1e5
            ));
        }
        return new MaplinkPoints(points.stream().toArray(MaplinkPoint[]::new));
    }
}
