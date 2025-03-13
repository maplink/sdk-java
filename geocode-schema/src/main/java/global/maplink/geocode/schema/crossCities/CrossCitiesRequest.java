package global.maplink.geocode.schema.crossCities;

import global.maplink.env.Environment;
import global.maplink.geocode.schema.GeocodeServiceRequest;
import global.maplink.http.request.Request;
import global.maplink.http.request.RequestBody;
import global.maplink.json.JsonMapper;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;

import static global.maplink.http.request.Request.post;

@Builder
@Getter
@ToString
@EqualsAndHashCode
@RequiredArgsConstructor(staticName = "of")
public class CrossCitiesRequest implements GeocodeServiceRequest {

    public static final String PATH = "/cross-cities";

    @Singular("point")
    private final List<Point> route;

    @Override
    public Request asHttpRequest(Environment environment, JsonMapper mapper) {
        return post(
                environment.withService(PATH),
                RequestBody.Json.of(this, mapper)
        );
    }

    public static Point point(BigDecimal latitude, BigDecimal longitude) {
        return Point.of(latitude, longitude);
    }

    public static Point point(double latitude, double longitude) {
        return Point.of(BigDecimal.valueOf(latitude), BigDecimal.valueOf(longitude));
    }

    @Getter
    @EqualsAndHashCode
    @ToString
    @RequiredArgsConstructor(staticName = "of")
    public static class Point {
        private final BigDecimal latitude;
        private final BigDecimal longitude;
    }
}
