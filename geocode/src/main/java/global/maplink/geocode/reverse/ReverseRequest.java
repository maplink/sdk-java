package global.maplink.geocode.reverse;


import lombok.*;

import java.util.List;

@Builder
@Getter
@ToString
@EqualsAndHashCode
public class ReverseRequest {
    @Singular
    private final List<Entry> entries;


    public static Entry entry(String id, double lat, double lon) {
        return Entry.builder().id(id).lat(lat).lon(lon).build();
    }

    public static Entry entry(String id, double lat, double lon, int distance) {
        return Entry.builder().id(id).lat(lat).lon(lon).distance(distance).build();
    }

    @Builder
    @Getter
    @ToString
    @EqualsAndHashCode
    public static class Entry {
        private final String id;
        private final double lat;
        private final double lon;
        private Integer distance;
    }

}
