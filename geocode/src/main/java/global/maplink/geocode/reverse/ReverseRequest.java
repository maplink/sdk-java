package global.maplink.geocode.reverse;


import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Builder
@Getter
@ToString
@EqualsAndHashCode
public class ReverseRequest {

    @Singular
    private final List<Entry> entries;

    public static Entry entry(double lat, double lon) {
        return Entry.builder()
                .lat(BigDecimal.valueOf(lat))
                .lon(BigDecimal.valueOf(lon))
                .build();
    }

    public static Entry entry(String id, double lat, double lon) {
        return Entry.builder()
                .id(id)
                .lat(BigDecimal.valueOf(lat))
                .lon(BigDecimal.valueOf(lon))
                .build();
    }

    public static Entry entry(String id, double lat, double lon, int distance) {
        return Entry.builder()
                .id(id)
                .lat(BigDecimal.valueOf(lat))
                .lon(BigDecimal.valueOf(lon))
                .distance(distance)
                .build();
    }

    public static Entry entry(BigDecimal lat, BigDecimal lon) {
        return Entry.builder().lat(lat).lon(lon).build();
    }

    public static Entry entry(String id, BigDecimal lat, BigDecimal lon) {
        return Entry.builder().id(id).lat(lat).lon(lon).build();
    }

    public static Entry entry(String id, BigDecimal lat, BigDecimal lon, int distance) {
        return Entry.builder().id(id).lat(lat).lon(lon).distance(distance).build();
    }

    @Builder
    @Getter
    @ToString
    @EqualsAndHashCode
    public static class Entry {
        private String id;
        private final BigDecimal lat;
        private final BigDecimal lon;
        private Integer distance;
    }

}
