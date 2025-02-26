package global.maplink.geocode.schema.v2.structured;

import global.maplink.env.Environment;
import global.maplink.geocode.schema.GeocodeSplittableRequest;
import global.maplink.geocode.schema.Type;
import global.maplink.http.request.Request;
import global.maplink.http.request.RequestBody;
import global.maplink.json.JsonMapper;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import lombok.val;

import java.util.List;
import java.util.stream.IntStream;

import static global.maplink.http.request.Request.post;
import static java.lang.Math.min;
import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;
import static java.util.stream.Collectors.toList;

public interface StructuredBaseRequest extends GeocodeSplittableRequest {

    static <T extends Single> T.SingleBuilder<?, ?> of(String id) {
        return Single.builder().id(id);
    }

    static Multi multi(Single... requests) {
        return new Multi(requests);
    }

    static Multi multi(List<Single> requests) {
        return new Multi(requests.toArray(new Single[0]));
    }


    @SuperBuilder
    @Getter
    @ToString
    @EqualsAndHashCode
    @AllArgsConstructor
    class Single implements StructuredBaseRequest {
        public static final String PATH = "geocode/v2/geocode";

        private String id;
        private String road;
        private Integer number;
        private String zipcode;
        private String district;
        private String city;
        private String state;
        private String acronym;
        private Type type;
        
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

        private final Single[] requests;

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
                    .map(l -> l.toArray(new Single[0]))
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
