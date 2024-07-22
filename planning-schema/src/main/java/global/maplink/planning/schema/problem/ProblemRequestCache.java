package global.maplink.planning.schema.problem;

import lombok.Getter;
import lombok.ToString;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.Collections.unmodifiableList;

@Getter
@ToString
public class ProblemRequestCache {

    private final List<String> siteNames;
    private final List<String> depotsNames;
    private final List<String> siteAndDepotNames;
    private final List<String> operationIds;
    private final List<String> vehicleTypeNames;
    private final List<String> vehicleNames;
    private final List<String> productNames;
    private final List<String> legislationProfileNames;
    private final List<String> logisticConstraintNames;
    private final List<String> incompatibilityGroupsNames;
    private final List<String> incompatibilityRelationshipNames;
    private final List<String> logisticZoneNames;
    private final Long startDate;
    private final List<VehicleRoute> vehicleRoutes;
    private final String defaultDepot;

    public ProblemRequestCache(Problem problem) {
        List<String> sites = streamOf(problem.getSites()).map(Site::getName).filter(Objects::nonNull).collect(Collectors.toList());
        List<String> depots = streamOf(problem.getDepots()).map(Site::getName).filter(Objects::nonNull).collect(Collectors.toList());

        List<String> sitesAndDepots = new ArrayList<>(sites);
        sitesAndDepots.addAll(depots);

        List<String> operations = streamOf(problem.getOperations()).map(Operation::getId).filter(Objects::nonNull)
                .collect(Collectors.toList());

        List<String> vehicleTypes = streamOf(problem.getVehicleTypes()).map(VehicleType::getName).filter(Objects::nonNull)
                .collect(Collectors.toList());

        List<String> vehicles = streamOf(problem.getVehicles()).map(Vehicle::getName).filter(Objects::nonNull).collect(Collectors.toList());

        List<String> products = streamOf(problem.getProducts()).map(Product::getName).filter(Objects::nonNull).collect(Collectors.toList());

        List<String> legislationProfiles = streamOf(problem.getLegislationProfiles()).map(LegislationProfile::getName)
                .filter(Objects::nonNull).collect(Collectors.toList());

        List<String> logisticConstraints = streamOf(problem.getLogisticConstraints()).map(LogisticConstraint::getName)
                .filter(Objects::nonNull).collect(Collectors.toList());

        List<String> incompatibilityGroups =
                streamOf(problem.getProducts()).map(Product::getIncompabilityGroup).filter(Objects::nonNull).collect(Collectors.toList());

        List<String> incompatibilityRelationships = streamOf(problem.getIncompabilityRelationships())
                .map(IncompabilityRelationship::getName).filter(Objects::nonNull).collect(Collectors.toList());

        List<String> logisticZones = streamOf(problem.getLogisticZones()).map(LogisticZone::getName).filter(Objects::nonNull).collect(
                Collectors.toList());

        List<VehicleRoute> vehicleRoutes = Collections.emptyList();

        if (problem.getSolutionToRebuild() != null && problem.getSolutionToRebuild().getVehicleRoutes() != null) {
            vehicleRoutes = problem.getSolutionToRebuild().getVehicleRoutes().stream().filter(Objects::nonNull)
                    .collect(Collectors.toList());
        }

        this.siteNames = unmodifiableList(sites);
        this.depotsNames = unmodifiableList(depots);
        this.siteAndDepotNames = unmodifiableList(sitesAndDepots);
        this.operationIds = unmodifiableList(operations);
        this.vehicleTypeNames = unmodifiableList(vehicleTypes);
        this.vehicleNames = unmodifiableList(vehicles);
        this.productNames = unmodifiableList(products);
        this.legislationProfileNames = unmodifiableList(legislationProfiles);
        this.logisticConstraintNames = unmodifiableList(logisticConstraints);
        this.startDate = problem.getStartDate();
        this.vehicleRoutes = unmodifiableList(vehicleRoutes);
        this.incompatibilityGroupsNames = unmodifiableList(incompatibilityGroups);
        this.incompatibilityRelationshipNames = unmodifiableList(incompatibilityRelationships);
        this.logisticZoneNames = unmodifiableList(logisticZones);
        this.defaultDepot = problem.getDefaultDepot();
    }

    private <T> Stream<T> streamOf(Collection<T> coll) {
        coll = coll == null ? Collections.emptyList() : coll;
        return coll.stream().filter(Objects::nonNull);
    }
}