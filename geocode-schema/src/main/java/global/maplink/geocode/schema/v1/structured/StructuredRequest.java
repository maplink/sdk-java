package global.maplink.geocode.schema.v1.structured;

import global.maplink.env.Environment;
import global.maplink.geocode.schema.SingleBase;
import global.maplink.geocode.schema.v2.Type;
import global.maplink.geocode.schema.v2.structured.StructuredBaseRequest;
import global.maplink.http.request.Request;
import global.maplink.http.request.RequestBody;
import global.maplink.json.JsonMapper;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import java.util.List;

import static global.maplink.http.request.Request.post;
import static java.util.Collections.singletonList;

public interface StructuredRequest extends StructuredBaseRequest {

    static StructuredRequest.Single.SingleBuilder of(String id) {
        return StructuredRequest.Single.builder().id(id);
    }

    static Single ofState(String id, String state) {
        return StructuredRequest.Single.builder()
                .id(id)
                .state(state)
                .type(Type.STATE)
                .build();
    }

    static Single ofCity(String id, String city, String state) {
        return StructuredRequest.Single.builder()
                .id(id)
                .state(state)
                .city(city)
                .type(Type.CITY)
                .build();
    }

    static Single ofDistrict(String id, String district, String city, String state) {
        return StructuredRequest.Single.builder()
                .id(id)
                .state(state)
                .city(city)
                .district(district)
                .type(Type.DISTRICT)
                .build();
    }

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

        @Override
        public List<StructuredRequest.Single> split() {
            return singletonList(this);
        }

    }

    @Getter
    @ToString
    @EqualsAndHashCode(callSuper = true)
    @SuperBuilder
    @Setter
    class Multi extends StructuredBaseRequest.Multi {
        private static final String PARAM_LAST_MILE = "lastMile";
        private boolean lastMile;

        public Multi(SingleBase[] requests) {
            super(requests);
            this.lastMile = false;
        }

        public Multi(SingleBase[] requests, boolean lastMile) {
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
