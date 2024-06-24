package global.maplink.planning.schema.problem;

import lombok.*;

import java.util.Date;
import java.util.List;
import java.util.Set;

import static lombok.AccessLevel.PRIVATE;

@Data
@Builder
@AllArgsConstructor(access = PRIVATE)
@NoArgsConstructor(force = true)
public class Problem {
    private final String clientId;

    @Singular
    private final List<Site> sites;

    @Singular
    private final List<Site> depots;

    @Singular
    private final List<Operation> operations;

    @Singular
    private final List<VehicleType> vehicleTypes;

    @Singular
    private final List<Vehicle> vehicles;

    @Singular
    private final List<Product> products;

    @Singular
    private final List<LegislationProfile> legislationProfiles;

    @Singular
    private final List<LogisticConstraint> logisticConstraints;

    @Singular
    private final List<IncompabilityRelationship> incompabilityRelationships;

    private final String optimizationProfile;

    private final String tripsProfile;

    private final String defaultDepot;

    private final Long startEnd;

    private final Boolean hasSolution;

    private final Date createdAt;

    private final Callback callback;

    private final SolutionToRebuild solutionToRebuild;

    private final List<LogisticZone> logisticZones;

    private final Set<String> restrictionZones;

    private final ProblemTrip trip;

    private final String calculationMode;

}
