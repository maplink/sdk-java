package global.maplink.trip.schema.problem;

import global.maplink.emission.schema.EmissionRequest;
import global.maplink.freight.schema.FreightCalculationRequest;
import global.maplink.place.schema.PlaceRouteRequest;
import lombok.*;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Set;

import static lombok.AccessLevel.PRIVATE;

@Data
@Builder
@RequiredArgsConstructor(staticName = "of")
@NoArgsConstructor(force = true, access = PRIVATE)
public class TripRequest {
    private final List<SitePoint> points;
    private final String calculationMode;
    private final Set<String> restrictionZones;
    private final Set<String> avoidanceTypes;
    private final TollRequest toll;
    private final CrossedBordersRequest crossedBorders;
    private final FreightCalculationRequest freight;
    private final EmissionRequest emission;
    private final PlaceRouteRequest place;
    private final LocalDate expireIn;
}
