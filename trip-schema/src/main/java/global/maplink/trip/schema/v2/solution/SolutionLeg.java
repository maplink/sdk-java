package global.maplink.trip.schema.v2.solution;

import global.maplink.geocode.schema.Address;
import global.maplink.trip.schema.v2.problem.SitePoint;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static lombok.AccessLevel.PRIVATE;

@Data
@Builder
@RequiredArgsConstructor
@NoArgsConstructor(force = true, access = PRIVATE)
public class SolutionLeg {
    private final Long distance;
    private final Long nominalDuration;
    private final Double averageSpeed;
    private final List<SitePoint> points;
    private final Address firstPointAddress;
    private final global.maplink.place.schema.LegResult placeCalculation;
    private final gloabl.maplink.toll.schema.LegResult tollCalculation;
}
