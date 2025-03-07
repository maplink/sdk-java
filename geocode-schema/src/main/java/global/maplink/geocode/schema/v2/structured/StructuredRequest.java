package global.maplink.geocode.schema.v2.structured;

import global.maplink.env.Environment;
import global.maplink.geocode.schema.GeocodeSplittableRequest;
import global.maplink.geocode.schema.v2.Type;
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

public interface StructuredRequest extends GeocodeSplittableRequest {

    static Single.SingleBuilder of(String id) {
        return Single.builder().id(id);
    }

    static Single.Single ofState(String id, String state) {
        return Single.builder()
                .id(id)
                .state(state)
                .type(Type.STATE)
                .build();
    }

    static Single.Single ofCity(String id, String city, String state) {
        return Single.builder()
                .id(id)
                .state(state)
                .city(city)
                .type(Type.CITY)
                .build();
    }

    static Single.Single ofDistrict(String id, String district, String city, String state) {
        return Single.builder()
                .id(id)
                .state(state)
                .city(city)
                .district(district)
                .type(Type.DISTRICT)
                .build();
    }

    static StructuredRequest.Multi multi(SingleBase... requests) {
        return new Multi(requests);
    }

    static StructuredRequest.Multi multi(List<SingleBase> requests) {
        return new Multi(requests.toArray(new Single[0]));
    }

    @SuperBuilder
    @Getter
    @ToString
    @EqualsAndHashCode
    class SingleBase implements StructuredRequest {
        public static final String PATH = "geocode/v2/geocode";
        private static final String PARAM_LAST_MILE = "lastMile";

        private String id;
        private String road;
        private Integer number;
        private String zipcode;
        private String district;
        private String city;
        private String state;
        private String acronym;
        private Type type;
        private boolean lastMile;

        @Override
        public List<SingleBase> split() {
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
    @EqualsAndHashCode(callSuper = false)
    class Single extends SingleBase implements StructuredRequest {
        public static final String PATH = "geocode/v1/geocode";
        private static final String PARAM_LAST_MILE = "lastMile";

        private boolean lastMile;

        @Override
        public Request asHttpRequest(Environment environment, JsonMapper mapper) {
            return post(
                    environment.withService(PATH),
                    RequestBody.Json.of(this, mapper)
            ).withQuery(PARAM_LAST_MILE, Boolean.toString(lastMile));
        }
    }


    @RequiredArgsConstructor
    @Getter
    @ToString
    @Data
    class Multi implements StructuredRequest {
        public static final String PATH = "geocode/v1/multi-geocode";
        public static final int REQ_LIMIT = 200;
        private static final String PARAM_LAST_MILE = "lastMile";

        private final SingleBase[] requests;
        private boolean lastMile;

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
            ).withQuery(PARAM_LAST_MILE, Boolean.toString(lastMile));
        }
    }
}
