package global.maplink.geocode.schema.geocode;

import global.maplink.MapLinkServiceRequest;
import global.maplink.env.Environment;
import global.maplink.geocode.schema.Type;
import global.maplink.http.request.Request;
import global.maplink.http.request.RequestBody;
import global.maplink.json.JsonMapper;
import lombok.*;

import static global.maplink.http.request.Request.post;

public interface GeocodeRequest extends MapLinkServiceRequest {

    static Single.SingleBuilder of(String id) {
        return Single.builder().id(id);
    }


    static Single.Single ofState(String id, String state) {
        return Single.builder()
                .id(id)
                .state(state)
                .build();
    }

    static Single.Single ofCity(String id, String city, String state) {
        return Single.builder()
                .id(id)
                .state(state)
                .city(city)
                .build();
    }

    static Single.Single ofDistrict(String id, String district, String city, String state) {
        return Single.builder()
                .id(id)
                .state(state)
                .city(city)
                .district(district)
                .build();
    }

    static GeocodeRequest.Multi multi(Single... requests) {
        return new Multi(requests);
    }

    @Builder
    @Getter
    @ToString
    @EqualsAndHashCode
    class Single implements GeocodeRequest {
        public static final String PATH = "geocode/v1/geocode";
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
        public Request asHttpRequest(Environment environment, JsonMapper mapper) {
            return post(
                    environment.withService(PATH),
                    RequestBody.Json.of(this, mapper)
            );
        }
    }

    @RequiredArgsConstructor
    @Getter
    @ToString
    class Multi implements GeocodeRequest {
        public static final String PATH = "geocode/v1/multi-geocode";

        private final Single[] requests;

        @Override
        public Request asHttpRequest(Environment environment, JsonMapper mapper) {
            return post(
                    environment.withService(PATH),
                    RequestBody.Json.of(requests, mapper)
            );
        }
    }
}
