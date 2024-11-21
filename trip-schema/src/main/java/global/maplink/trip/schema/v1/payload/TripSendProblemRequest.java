package global.maplink.trip.schema.v1.payload;

import global.maplink.MapLinkServiceRequest;
import global.maplink.emission.schema.EmissionRequest;
import global.maplink.env.Environment;
import global.maplink.freight.schema.FreightCalculationRequest;
import global.maplink.http.Response;
import global.maplink.http.request.Request;
import global.maplink.http.request.RequestBody;
import global.maplink.json.JsonMapper;
import global.maplink.place.schema.PlaceRouteRequest;
import global.maplink.trip.schema.v1.exception.TripCalculationRequestException;
import global.maplink.trip.schema.v2.features.avoidance.AvoidanceBehavior;
import global.maplink.trip.schema.v2.features.crossedBorders.CrossedBordersRequest;
import global.maplink.trip.schema.v2.features.turnByTurn.TurnByTurnRequest;
import global.maplink.trip.schema.v2.problem.CalculationMode;
import global.maplink.trip.schema.v2.problem.SitePoint;
import global.maplink.trip.schema.v2.problem.TollRequest;
import global.maplink.trip.schema.v2.problem.TripProblem;
import global.maplink.validations.ValidationViolation;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.function.Function;

import static global.maplink.http.request.Request.post;
import static global.maplink.trip.schema.v1.exception.TripErrorType.*;

@Getter
@ToString
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor(force = true)
public class TripSendProblemRequest extends TripProblem implements MapLinkServiceRequest<TripSolutionId> {
    public static final String PATH = "trip/v1/problems";
    private static final int MINIMUM_POINTS = 2;

    private final String clientId;
    private final String profileName;
    private final List<SpeedPreference> speedPreferences;
    private final VehicleSpecification vehicleSpecification;
    private final Callback callback;

    @Builder
    public TripSendProblemRequest(
            @Singular List<SitePoint> points,
            CalculationMode calculationMode,
            @Singular Set<String> restrictionZones,
            @Singular Set<AvoidanceType> avoidanceTypes,
            AvoidanceBehavior avoidanceBehavior,
            TollRequest toll,
            CrossedBordersRequest crossedBorders,
            FreightCalculationRequest freight,
            EmissionRequest emission,
            PlaceRouteRequest place,
            TurnByTurnRequest turnByTurn,
            String clientId,
            String profileName,
            @Singular List<SpeedPreference> speedPreferences,
            VehicleSpecification vehicleSpecification,
            Callback callback
    ) {
        super(points, calculationMode, speedPreferences, restrictionZones, avoidanceTypes, avoidanceBehavior, toll, crossedBorders, freight, emission, place, turnByTurn);
        this.clientId = clientId;
        this.profileName = profileName;
        this.speedPreferences = speedPreferences;
        this.vehicleSpecification = vehicleSpecification;
        this.callback = callback;
    }

    @Override
    public Request asHttpRequest(Environment environment, JsonMapper mapper) {
        return post(
                environment.withService(PATH),
                RequestBody.Json.of(this, mapper)
        );
    }

    @Override
    public Function<Response, TripSolutionId> getResponseParser(JsonMapper mapper) {
        return r -> r.parseBodyObject(mapper, TripSolutionId.class);
    }

    @Override
    public List<ValidationViolation> validate() {
        List<ValidationViolation> errors = new ArrayList<>();

        if (profileName == null || profileName.isEmpty()) {
            errors.add(PROFILE_NAME_EMPTY);
        }

        if (callback != null && callback.getUrl() == null) {
            errors.add(CALLBACK_DOES_NOT_HAVE_URL);
        }

        if (toll != null && toll.getVehicleType() == null) {
            errors.add(TOLL_PARAMETERS_DOES_NOT_HAVE_VEHICLE_TYPE);
        }

        if (crossedBorders != null && crossedBorders.getLevel() == null) {
            errors.add(CROSSED_BORDERS_DOES_NOT_HAVE_LEVEL);
        }

        if (speedPreferences != null && !speedPreferences.isEmpty()) {
            validateSpeedPreferences(errors);
        }

        if (vehicleSpecification != null) {
            errors.addAll(vehicleSpecification.validate());
        }

        if (freight != null) {
            errors.addAll(freight.validate());
        }

        if (place != null) {
            errors.addAll(place.validate());
        }

        if (turnByTurn != null) {
            errors.addAll(turnByTurn.validate());
        }

        return errors;
    }

    public void validateWithPoints() {
        if (points == null || points.size() < MINIMUM_POINTS) {
            throw new TripCalculationRequestException(ROUTE_POINTS_LESS_THAN_TWO);
        }

        validate();
    }

    private void validateSpeedPreferences(List<ValidationViolation> errors) {
        final long roadTypesCount = speedPreferences.stream()
                .map(SpeedPreference::getRoadType)
                .distinct()
                .count();

        if (roadTypesCount != RoadType.values().length) {
            errors.add(ROAD_TYPE_ELEMENTS_EMPTY);
        }
    }

}
