package global.maplink.geocode.schema.v1.structured;

import global.maplink.env.Environment;
import global.maplink.geocode.schema.v1.Type;
import global.maplink.geocode.schema.v2.structured.StructuredBaseRequest;
import global.maplink.http.request.Request;
import global.maplink.json.JsonMapper;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import java.util.List;

public interface StructuredRequest extends StructuredBaseRequest {

    static Single.SingleBuilder<?,?> of(String id) {
        return Single.builder().id(id);
    }

    static Single ofState(String id, String state) {
        return Single.builder()
                .id(id)
                .state(state)
                .type(Type.STATE)
                .build();
    }

    static Single ofCity(String id, String city, String state) {
        return Single.builder()
                .id(id)
                .state(state)
                .city(city)
                .type(Type.CITY)
                .build();
    }

    static Single ofDistrict(String id, String district, String city, String state) {
        return Single.builder()
                .id(id)
                .state(state)
                .city(city)
                .district(district)
                .type(Type.DISTRICT)
                .build();
    }

    static StructuredRequest.Multi multi(Single... requests) {
        return new Multi(requests);
    }

    static StructuredRequest.Multi multi(List<Single> requests) {
        return new Multi(requests.toArray(new Single[0]));
    }



    @SuperBuilder
    @Getter
    @ToString
    @EqualsAndHashCode(callSuper = true)
    class Single extends StructuredBaseRequest.Single implements StructuredRequest {
        private static final String PARAM_LAST_MILE = "lastMile";
        private boolean lastMile;

        @Override
        public Request asHttpRequest(Environment environment, JsonMapper mapper) {
            return super.asHttpRequest(environment, mapper)
                    .withQuery(PARAM_LAST_MILE, Boolean.toString(lastMile));
        }
    }

    @Getter
    @ToString
    @EqualsAndHashCode(callSuper = true)
    @SuperBuilder
    @Setter
    class Multi extends StructuredBaseRequest.Multi implements StructuredRequest {
        private static final String PARAM_LAST_MILE = "lastMile";
        private boolean lastMile;

        public Multi(StructuredRequest.Single[] requests) {
            super(requests);
            this.lastMile = false;
        }

        public Multi(StructuredRequest.Single[] requests, boolean lastMile) {
            super(requests);
            this.lastMile = lastMile;
        }

        @Override
        public Request asHttpRequest(Environment environment, JsonMapper mapper) {
            return super.asHttpRequest(environment, mapper)
                    .withQuery(PARAM_LAST_MILE, Boolean.toString(lastMile));
        }
    }
}
