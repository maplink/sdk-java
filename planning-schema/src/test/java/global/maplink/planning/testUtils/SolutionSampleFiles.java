package global.maplink.planning.testUtils;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor

public enum SolutionSampleFiles implements TestFiles {

    ACTIVITY("planning.json/samples/solution/activity.json"),
    INDICATORS("planning.json/samples/solution/indicators.json"),
    PENDING_TASKS("planning.json/samples/solution/pendingTasks.json"),
    POSSIBLE_CAUSE_REJECT("planning.json/samples/solution/possibleCauseReject.json"),
    POSSIBLE_CAUSE_REJECT_GROUP("planning.json/samples/solution/possibleCauseRejectGroup.json"),
    SOLUTION("planning.json/samples/solution/solution.json");

    private final String filePath;
}
