package global.maplink.trip.schema.v2.problem;

import global.maplink.emission.schema.EmissionRequest;
import global.maplink.freight.schema.FreightCalculationRequest;
import global.maplink.place.schema.PlaceRouteRequest;
import global.maplink.trip.schema.v1.payload.AvoidanceType;
import global.maplink.trip.schema.v2.features.avoidance.AvoidanceBehavior;
import global.maplink.trip.schema.v2.features.crossedBorders.CrossedBordersRequest;
import global.maplink.trip.schema.v2.features.turnByTurn.TurnByTurnRequest;
import global.maplink.validations.Validable;
import global.maplink.validations.ValidationViolation;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import static global.maplink.trip.schema.v2.features.avoidance.AvoidanceBehavior.FAIL;
import static global.maplink.trip.schema.v2.problem.CalculationMode.THE_FASTEST;
import static java.util.Collections.emptySet;
import static java.util.Optional.ofNullable;

@Getter
@ToString
@EqualsAndHashCode
public class TripProblem implements Validable {

    protected final List<SitePoint> points;
    protected final CalculationMode calculationMode;
    protected final Set<String> restrictionZones;
    protected final Set<AvoidanceType> avoidanceTypes;
    protected final AvoidanceBehavior avoidanceBehavior;
    protected final TollRequest toll;
    protected final CrossedBordersRequest crossedBorders;
    protected final FreightCalculationRequest freight;
    protected final EmissionRequest emission;
    protected final PlaceRouteRequest place;
    protected final TurnByTurnRequest turnByTurn;

    public TripProblem(
            List<SitePoint> points,
            CalculationMode calculationMode,
            Set<String> restrictionZones,
            Set<AvoidanceType> avoidanceTypes,
            AvoidanceBehavior avoidanceBehavior,
            TollRequest toll,
            CrossedBordersRequest crossedBorders,
            FreightCalculationRequest freight,
            EmissionRequest emission,
            PlaceRouteRequest place,
            TurnByTurnRequest turnByTurn
    ) {
        this.points = points;
        this.calculationMode = ofNullable(calculationMode).orElse(THE_FASTEST);
        this.restrictionZones = ofNullable(restrictionZones).orElse(emptySet());
        this.avoidanceTypes = ofNullable(avoidanceTypes).orElse(emptySet());
        this.avoidanceBehavior= ofNullable(avoidanceBehavior).orElse(FAIL);
        this.toll = toll;
        this.crossedBorders = crossedBorders;
        this.freight = freight;
        this.emission = emission;
        this.place = place;
        this.turnByTurn = turnByTurn;
    }

    public TripProblem() {
        this.points = null;
        this.calculationMode = THE_FASTEST;
        this.restrictionZones = emptySet();
        this.avoidanceTypes = emptySet();
        this.avoidanceBehavior = FAIL;
        this.toll = null;
        this.crossedBorders = null;
        this.freight = null;
        this.emission = null;
        this.place = null;
        this.turnByTurn = null;
    }

    @Override
    public List<ValidationViolation> validate() {
        List<ValidationViolation> errors = new LinkedList<>();
        if (toll != null) {
            errors.addAll(toll.validate());
            errors.addAll(toll.validateVariableAxles(points));
        }
        if (turnByTurn != null) {
            errors.addAll(turnByTurn.validate());
        }
        return errors;
    }
}

