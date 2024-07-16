package global.maplink.planning.testUtils;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor

public enum CommonSampleFiles implements TestFiles{

    COMPARTMENT_SOLUTION("planning.json/samples/commons/compartmentSolution.json"),
    COMPARTMENT_SOLUTION_GROUP("planning.json/samples/commons/compartmentSolutionGroup.json"),
    ROUTE("planning.json/samples/commons/route.json"),
    VIOLATION_CONSTRAINT("planning.json/samples/commons/violationConstraint.json");

    private final String filePath;
}
