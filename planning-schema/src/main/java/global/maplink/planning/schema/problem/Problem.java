package global.maplink.planning.schema.problem;

import global.maplink.planning.schema.exception.PlanningUpdateViolation;
import global.maplink.trip.schema.v1.exception.TripCalculationRequestException;
import global.maplink.trip.schema.v2.problem.CalculationMode;
import global.maplink.validations.ValidationViolation;
import lombok.*;

import java.io.IOException;
import java.util.*;

import static java.util.Objects.isNull;
import static lombok.AccessLevel.PRIVATE;

@Data
@Builder
@AllArgsConstructor(access = PRIVATE)
@NoArgsConstructor(force = true)
public class Problem {

    private final String clientId;
    @Singular
    private List<Site> sites;
    @Singular
    private List<Site> depots;
    @Singular
    private List<Operation> operations;
    @Singular
    private List<VehicleType> vehicleTypes;
    @Singular
    private List<Vehicle> vehicles;
    @Singular
    private List<Product> products;
    @Singular
    private List<LegislationProfile> legislationProfiles;
    @Singular
    private List<LogisticConstraint> logisticConstraints;
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
    private final CalculationMode calculationMode;

    public List<ValidationViolation> validate() {
        List<ValidationViolation> violations = new LinkedList<>();

        if(sites.isEmpty()){
            violations.add(PlanningUpdateViolation.of("problem.sites"));
        }

        if(depots.isEmpty()){
            violations.add(PlanningUpdateViolation.of("problem.sites"));
        }

        if(operations.isEmpty()){
            violations.add(PlanningUpdateViolation.of("problem.sites"));
        }

        if(vehicleTypes.isEmpty()){
            violations.add(PlanningUpdateViolation.of("problem.sites"));
        }

        if(vehicles.isEmpty()){
            violations.add(PlanningUpdateViolation.of("problem.sites"));
        }

        if(products.isEmpty()){
            violations.add(PlanningUpdateViolation.of("problem.sites"));
        }

        if(legislationProfiles.isEmpty()){
            violations.add(PlanningUpdateViolation.of("problem.sites"));
        }

        if(logisticConstraints.isEmpty()){
            violations.add(PlanningUpdateViolation.of("problem.sites"));
        }

        return violations;
    }

    public List<Site> getSites() {

        if (sites == null) {
            sites = new ArrayList<>();
        }
        return sites;
    }

    public List<Site> getDepots() {

        if (depots == null) {
            depots = new ArrayList<>();
        }
        return depots;
    }

    public List<Operation> getOperations() {

        if (operations == null) {
            operations = new ArrayList<>();
        }
        return operations;
    }

    public List<VehicleType> getVehicleTypes() {

        if (vehicleTypes == null) {
            vehicleTypes = new ArrayList<>();
        }
        return vehicleTypes;
    }

    public List<Vehicle> getVehicles() {

        if (vehicles == null) {
            vehicles = new ArrayList<>();
        }
        return vehicles;
    }

    public List<Product> getProducts() {

        if (products == null) {
            products = new ArrayList<>();
        }
        return products;
    }

    public List<LegislationProfile> getLegislationProfiles() {

        if (legislationProfiles == null) {
            legislationProfiles = new ArrayList<>();
        }
        return legislationProfiles;
    }

    public List<LogisticConstraint> getLogisticConstraints() {

        if (logisticConstraints == null) {
            logisticConstraints = new ArrayList<>();
        }
        return logisticConstraints;
    }
}
