package global.maplink.geocode.schema.v2.reverse;


import global.maplink.env.Environment;
import global.maplink.geocode.schema.GeocodeSplittableRequest;
import global.maplink.http.request.Request;
import global.maplink.http.request.RequestBody;
import global.maplink.json.JsonMapper;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.IntStream;

import static global.maplink.http.request.Request.post;
import static java.lang.Math.min;
import static java.util.Collections.singletonList;
import static java.util.stream.Collectors.toList;

@SuperBuilder
@Getter
@ToString
@EqualsAndHashCode
@RequiredArgsConstructor(staticName = "of")
public class ReverseBaseRequest implements GeocodeSplittableRequest {
    public static final int ENTRY_LIMIT = 200;
    public static final String PATH = "geocode/v1/reverse";

    @Singular
    private final List<Entry> entries;

    @Override
    public List<ReverseBaseRequest> split() {
        if (entries.size() < entryLimit()) {
            return singletonList(this);
        }
        val parts = (entries.size() / entryLimit()) + 1;
        return IntStream.range(0, parts)
                .map(i -> i * entryLimit())
                .mapToObj(i -> entries.subList(i, min(i + 200, entries.size())))
                .map(ReverseBaseRequest::new)
                .collect(toList());
    }

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

    @Override
    public Request asHttpRequest(Environment environment, JsonMapper mapper) {
        return post(
                environment.withService(path()),
                RequestBody.Json.of(entries, mapper)
        );
    }

    protected int entryLimit() {
        return ENTRY_LIMIT;
    }

    protected String path() {
        return PATH;
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
