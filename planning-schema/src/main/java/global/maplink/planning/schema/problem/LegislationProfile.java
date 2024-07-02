package global.maplink.planning.schema.problem;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

import static lombok.AccessLevel.PRIVATE;

@Data
@Builder
@AllArgsConstructor(access = PRIVATE)
@NoArgsConstructor(force = true)
public class LegislationProfile {

    private final String name;
    private final Integer maxContinuousDrivingTime;
    private final Integer drivingPauseDuration;
    private final List<Integer> drivingPauseDurationCuts;
    private final Integer maxContinuousWorkingTime;
    private final Integer workingPauseDuration;
    private final List<Integer> workingPauseDurationCuts;
    private final Integer maxDrivingTimeBetweenTwoRests;
    private final Integer drivingRestDuration;
    private final List<Integer> drivingRestDurationCuts;
    private final Integer maxWorkingTimeBetweenTwoRests;
    private final Integer workingRestDuration;
    private final List<Integer> workingRestDurationCuts;
    private final Integer maxWaitingTime;
    private final boolean waitingIsWorking = false;
}
