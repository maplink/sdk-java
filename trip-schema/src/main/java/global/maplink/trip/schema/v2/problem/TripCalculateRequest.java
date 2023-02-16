package global.maplink.trip.schema.v2.problem;

import global.maplink.MapLinkServiceRequest;
import global.maplink.emission.schema.EmissionRequest;
import global.maplink.env.Environment;
import global.maplink.freight.schema.FreightCalculationRequest;
import global.maplink.http.Response;
import global.maplink.http.request.Request;
import global.maplink.http.request.RequestBody;
import global.maplink.json.JsonMapper;
import global.maplink.place.schema.PlaceRouteRequest;
import global.maplink.trip.schema.v2.features.crossedBorders.CrossedBordersRequest;
import global.maplink.trip.schema.v2.solution.TripSolution;
import lombok.*;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Set;
import java.util.function.Function;

import static global.maplink.http.request.Request.post;
import static global.maplink.trip.schema.v2.problem.CalculationMode.THE_FASTEST;
import static lombok.AccessLevel.PRIVATE;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor(force = true, access = PRIVATE)
public class TripCalculateRequest implements MapLinkServiceRequest<TripSolution> {
    public static final String PATH = "trip/v2/calculations";
    @Singular
    private final List<SitePoint> points;
    @Builder.Default
    private final CalculationMode calculationMode = THE_FASTEST;
    @Singular
    private final Set<String> restrictionZones;
    @Singular
    private final Set<String> avoidanceTypes;
    private final TollRequest toll;
    private final CrossedBordersRequest crossedBorders;
    private final FreightCalculationRequest freight;
    private final EmissionRequest emission;
    private final PlaceRouteRequest place;
    private final OffsetDateTime expireIn;

    @Override
    public Request asHttpRequest(Environment environment, JsonMapper mapper) {
        return post(
                environment.withService(PATH),
                RequestBody.Json.of(this, mapper)
        );
    }

    @Override
    public Function<Response, TripSolution> getResponseParser(JsonMapper mapper) {
        return response -> response.parseBodyObject(mapper, TripSolution.class);
    }
}
