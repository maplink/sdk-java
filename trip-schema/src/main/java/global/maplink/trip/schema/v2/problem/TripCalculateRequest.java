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
import global.maplink.trip.schema.v1.payload.AvoidanceType;
import global.maplink.trip.schema.v1.payload.SpeedPreference;
import global.maplink.trip.schema.v2.features.avoidance.AvoidanceBehavior;
import global.maplink.trip.schema.v2.features.crossedBorders.CrossedBordersRequest;
import global.maplink.trip.schema.v2.features.turnByTurn.TurnByTurnRequest;
import global.maplink.trip.schema.v2.solution.TripSolution;
import lombok.*;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Set;
import java.util.function.Function;

import static global.maplink.http.request.Request.post;

@Getter
@ToString
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor(force = true)
public class TripCalculateRequest extends TripProblem implements MapLinkServiceRequest<TripSolution> {
    public static final String PATH = "trip/v2/calculations";
    private final OffsetDateTime expireIn;

    @Builder
    public TripCalculateRequest(
            @Singular List<SitePoint> points,
            CalculationMode calculationMode,
            @Singular List<SpeedPreference> speedPreferences,
            @Singular Set<String> restrictionZones,
            @Singular Set<AvoidanceType> avoidanceTypes,
            AvoidanceBehavior avoidanceBehavior,
            TollRequest toll,
            CrossedBordersRequest crossedBorders,
            FreightCalculationRequest freight,
            EmissionRequest emission,
            PlaceRouteRequest place,
            TurnByTurnRequest turnByTurn,
            OffsetDateTime expireIn
    ) {
        super(points, calculationMode, speedPreferences, restrictionZones, avoidanceTypes, avoidanceBehavior,
                toll, crossedBorders, freight, emission, place, turnByTurn);
        this.expireIn = expireIn;
    }


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
