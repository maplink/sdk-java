package global.maplink.geocode.geocode;

import global.maplink.geocode.schema.Type;
import lombok.*;

public interface GeocodeRequest {

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
        private String id;
        private String road;
        private Integer number;
        private String zipcode;
        private String district;
        private String city;
        private String state;
        private String acronym;
        private Type type;
    }

    @RequiredArgsConstructor
    @Getter
    @ToString
    class Multi implements GeocodeRequest {
        private final Single[] requests;
    }
}
