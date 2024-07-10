package global.maplink.planning.testUtils;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor

public enum ProblemSampleFiles implements TestFiles {

    CALLBACK("planning.json/samples/problem/callback.json"),
    AVAILABLE_PERIOD("planning.json/samples/problem/availablePeriod.json"),
    COMPARTMENT("planning.json/samples/problem/compartment.json"),
    COMPARTMENT_CONFIGURATION("planning.json/samples/problem/compartmentConfiguration.json"),
    COORDINATES("planning.json/samples/problem/coordinates.json"),
    INCOMPABILITY_RELATIONSHIP("planning.json/samples/problem/incompabilityRelationship.json"),
    LEGISLATION_PROFILE("planning.json/samples/problem/legislationProfile.json"),
    LOGISTIC_CONSTRAINT("planning.json/samples/problem/logisticConstraint.json"),
    LOGISTIC_ZONE("planning.json/samples/problem/logisticZone.json"),
    OPERATION("planning.json/samples/problem/operation.json"),
    PROBLEM("planning.json/samples/problem/problem.json"),
    PROBLEM_RESPONSE("planning.json/samples/problem/problemResponse.json"),
    PRODUCT("planning.json/samples/problem/product.json"),
    SITE("planning.json/samples/problem/site.json"),
    SOLUTION_TO_REBUILD("planning.json/samples/problem/solutionToRebuild.json"),
    TIME_WINDOW("planning.json/samples/problem/timeWindow.json"),
    VEHICLE("planning.json/samples/problem/vehicle.json"),
    VEHICLE_ROUTE("planning.json/samples/problem/vehicleRoute.json"),
    VEHICLE_TYPE("planning.json/samples/problem/vehicleType.json");
    private final String filePath;
}
