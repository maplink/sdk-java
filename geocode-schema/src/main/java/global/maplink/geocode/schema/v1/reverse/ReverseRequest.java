package global.maplink.geocode.schema.v1.reverse;


import global.maplink.env.Environment;
import global.maplink.geocode.schema.GeocodeSplittableRequest;
import global.maplink.http.request.Request;
import global.maplink.http.request.RequestBody;
import global.maplink.json.JsonMapper;
import lombok.*;

import java.util.List;
import java.util.stream.IntStream;

import static global.maplink.http.request.Request.post;
import static java.lang.Math.min;
import static java.util.Collections.singletonList;
import static java.util.stream.Collectors.toList;

@Builder
@Getter
@ToString
@EqualsAndHashCode
public class ReverseRequest implements GeocodeSplittableRequest {
    public static final String PATH = "geocode/v1/reverse";
    public static final int ENTRY_LIMIT = 200;

    @Singular
    private final List<global.maplink.geocode.schema.v2.reverse.ReverseRequest.Entry> entries;

    public static ReverseRequest of(List<global.maplink.geocode.schema.v2.reverse.ReverseRequest.Entry> entries) {
        return global.maplink.geocode.schema.v1.reverse.ReverseRequest.builder()
                .entries(entries)
                .build();
    }

    @Override
    public List<ReverseRequest> split() {
        if (entries.size() < ENTRY_LIMIT) {
            return singletonList(this);
        }
        val parts = (entries.size() / ENTRY_LIMIT) + 1;
        return IntStream.range(0, parts)
                .map(i -> i * ENTRY_LIMIT)
                .mapToObj(i -> entries.subList(i, min(i + 200, entries.size())))
                .map(global.maplink.geocode.schema.v1.reverse.ReverseRequest::new)
                .collect(toList());
    }

    @Override
    public Request asHttpRequest(Environment environment, JsonMapper mapper) {
        return post(
                environment.withService(PATH),
                RequestBody.Json.of(entries, mapper)
        );
    }
}


