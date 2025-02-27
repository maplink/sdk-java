package global.maplink.geocode.schema.v2.structured;

import global.maplink.env.Environment;
import global.maplink.geocode.schema.GeocodeSplittableRequest;
import global.maplink.geocode.schema.SingleBase;
import global.maplink.geocode.schema.v2.MainLocation;
import global.maplink.http.request.Request;
import global.maplink.http.request.RequestBody;
import global.maplink.json.JsonMapper;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.List;
import java.util.stream.IntStream;

import static global.maplink.http.request.Request.post;
import static java.lang.Math.min;
import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;
import static java.util.stream.Collectors.toList;

public interface StructuredBaseRequest extends GeocodeSplittableRequest {

    static Multi multi(SingleBase... requests) {
        return new Multi(requests);
    }

    static Multi multi(List<SingleBase> requests) {
        return new Multi(requests.toArray(new SingleBase[0]));
    }

    @SuperBuilder
    @Getter
    @ToString
    @EqualsAndHashCode(callSuper = true)
    class Single extends SingleBase implements StructuredBaseRequest {
        public static final String PATH = "geocode/v2/geocode";

        private MainLocation mainLocation;

        @Override
        public List<Single> split() {
            return singletonList(this);
        }

        @Override
        public Request asHttpRequest(Environment environment, JsonMapper mapper) {
            return post(
                    environment.withService(PATH),
                    RequestBody.Json.of(this, mapper)
            );
        }

    }

    @SuperBuilder
    @Getter
    @ToString
    @EqualsAndHashCode
    @AllArgsConstructor
    class Multi implements StructuredBaseRequest {
        public static final String PATH = "geocode/v2/multi-geocode";
        public static final int REQ_LIMIT = 200;

        private final SingleBase[] requests;

        @Override
        public List<Multi> split() {
            if (requests.length < REQ_LIMIT) {
                return singletonList(this);
            }
            val parts = (requests.length / REQ_LIMIT) + 1;
            val reqList = asList(requests);
            return IntStream.range(0, parts)
                    .map(i -> i * REQ_LIMIT)
                    .mapToObj(i -> reqList.subList(i, min(i + 200, reqList.size())))
                    .map(l -> l.toArray(new SingleBase[0]))
                    .map(Multi::new)
                    .collect(toList());
        }

        @Override
        public Request asHttpRequest(Environment environment, JsonMapper mapper) {
            return post(
                    environment.withService(PATH),
                    RequestBody.Json.of(requests, mapper)
            );
        }
    }
}
