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
    private final Integer maxContinuousDrivingTime; //shortBreakDrivingThreshold
    private final Integer drivingPauseDuration; //shortBreakDrivingDuration
    private final List<Integer> drivingPauseDurationCuts; //shortBreakDrivingCuts
    private final Integer maxContinuousWorkingTime; //shortBreakWorkingThreshold
    private final Integer workingPauseDuration; //shortBreakWorkingDuration
    private final List<Integer> workingPauseDurationCuts; //shortBreakWorkingCuts
    private final Integer maxDrivingTimeBetweenTwoRests; //longBreakDrivingThreshold
    private final Integer drivingRestDuration; //longBreakDrivingDuration
    private final List<Integer> drivingRestDurationCuts; //longBreakDrivingCuts
    private final Integer maxWorkingTimeBetweenTwoRests; //longBreakWorkingThreshold
    private final Integer workingRestDuration; //longBreakWorkingDuration
    private final List<Integer> workingRestDurationCuts; //longBreakWorkingCuts
    private final Integer maxWaitingTime; //maxWaitingTime
    private final static boolean waitingIsWorking = false; //waitingIsWorking
}
