package global.maplink.domain;

import ch.hsr.geohash.GeoHash;
import lombok.Value;

import static ch.hsr.geohash.GeoHash.fromGeohashString;
import static ch.hsr.geohash.GeoHash.geoHashStringWithCharacterPrecision;

@Value
public class MaplinkPoint {
    public static final int DEFAULT_GEOHASH_SIZE = 9;
    private static final int LATITUDE = 0;
    private static final int LONGITUDE = 1;

    double latitude;
    double longitude;

    public String toGeohash() {
        return toGeohash(DEFAULT_GEOHASH_SIZE);
    }

    public String toGeohash(int size) {
        return geoHashStringWithCharacterPrecision(latitude, longitude, size);
    }

    public double[] toArray() {
        return new double[]{
                latitude,
                longitude
        };
    }

    public static MaplinkPoint from(double[] values) {
        if (values.length != 2) {
            throw new IllegalArgumentException("A point needs 2 and only 2 coordinates array");
        }
        return new MaplinkPoint(
                values[LATITUDE],
                values[LONGITUDE]
        );
    }

    public static MaplinkPoint fromGeohash(String geohash) {
        final GeoHash decodedHash = fromGeohashString(geohash);
        return new MaplinkPoint(
                decodedHash.getBoundingBoxCenter().getLatitude(),
                decodedHash.getBoundingBoxCenter().getLongitude()
        );
    }

}
