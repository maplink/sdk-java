package global.maplink.trip.schema.v2.problem;

import global.maplink.emission.schema.EmissionRequest;
import global.maplink.freight.schema.FreightCalculationRequest;
import global.maplink.place.schema.PlaceRouteRequest;
import global.maplink.trip.schema.v1.payload.AvoidanceType;
import global.maplink.trip.schema.v2.features.crossedBorders.CrossedBordersRequest;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;
import java.util.Set;

import static global.maplink.trip.schema.v2.problem.CalculationMode.THE_FASTEST;
import static java.util.Collections.emptySet;
import static java.util.Optional.ofNullable;

@Getter
@ToString
@EqualsAndHashCode
@NoArgsConstructor(force = true)
public class TripProblem {

    protected final List<SitePoint> points;
    protected final CalculationMode calculationMode;
    protected final Set<String> restrictionZones;
    protected final Set<AvoidanceType> avoidanceTypes;
    protected final TollRequest toll;
    protected final CrossedBordersRequest crossedBorders;
    protected final FreightCalculationRequest freight;
    protected final EmissionRequest emission;
    protected final PlaceRouteRequest place;

    public TripProblem(
            List<SitePoint> points,
            CalculationMode calculationMode,
            Set<String> restrictionZones,
            Set<AvoidanceType> avoidanceTypes,
            TollRequest toll,
            CrossedBordersRequest crossedBorders,
            FreightCalculationRequest freight,
            EmissionRequest emission,
            PlaceRouteRequest place
    ) {
        this.points = points;
        this.calculationMode = ofNullable(calculationMode).orElse(THE_FASTEST);
        this.restrictionZones = ofNullable(restrictionZones).orElse(emptySet());
        this.avoidanceTypes = ofNullable(avoidanceTypes).orElse(emptySet());
        this.toll = toll;
        this.crossedBorders = crossedBorders;
        this.freight = freight;
        this.emission = emission;
        this.place = place;
    }
}

