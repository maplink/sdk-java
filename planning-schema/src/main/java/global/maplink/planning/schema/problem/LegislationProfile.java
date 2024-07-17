package global.maplink.planning.schema.problem;

import global.maplink.planning.schema.exception.PlanningUpdateViolation;
import global.maplink.planning.schema.validator.FieldValidator;
import global.maplink.validations.ValidationViolation;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.LinkedList;
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
    private final boolean waitingIsWorking = false; //waitingIsWorking

    public List<ValidationViolation> validate() {
        List<ValidationViolation> violations = new LinkedList<>();

        if(!FieldValidator.isGreaterThanZero(maxContinuousDrivingTime)){
            violations.add(PlanningUpdateViolation.of("legislationProfile.maxContinuousDrivingTime"));
        }

        if(!FieldValidator.isGreaterThanZero(drivingPauseDuration)){
            violations.add(PlanningUpdateViolation.of("legislationProfile.drivingPauseDuration"));
        }

        if(!FieldValidator.isGreaterThanZero(maxContinuousWorkingTime)){
            violations.add(PlanningUpdateViolation.of("legislationProfile.maxContinuousWorkingTime"));
        }

        if(!FieldValidator.isGreaterThanZero(workingPauseDuration)){
            violations.add(PlanningUpdateViolation.of("legislationProfile.workingPauseDuration"));
        }

        if(!FieldValidator.isNotNegative(maxDrivingTimeBetweenTwoRests)){
            violations.add(PlanningUpdateViolation.of("legislationProfile.maxDrivingTimeBetweenTwoRests"));
        }

        if(!FieldValidator.isNotNegative(drivingRestDuration)){
            violations.add(PlanningUpdateViolation.of("legislationProfile.maxDrivingTimeBetweenTwoRests"));
        }

        if(!FieldValidator.isNotNegative(maxWorkingTimeBetweenTwoRests)){
            violations.add(PlanningUpdateViolation.of("legislationProfile.maxDrivingTimeBetweenTwoRests"));
        }

        if(!FieldValidator.isNotNegative(workingRestDuration)){
            violations.add(PlanningUpdateViolation.of("legislationProfile.maxDrivingTimeBetweenTwoRests"));
        }

        if(!FieldValidator.isNotNegative(maxWaitingTime)){
            violations.add(PlanningUpdateViolation.of("legislationProfile.maxDrivingTimeBetweenTwoRests"));
        }

        sumOfCutsEqualToDrivingPauseDuration(violations, drivingPauseDuration, drivingPauseDurationCuts);
        sumOfCutsEqualToWorkingPauseDuration(violations, workingPauseDuration, workingPauseDurationCuts);
        sumOfCutsEqualToDrivingRestDuration(violations, drivingRestDuration, drivingRestDurationCuts);
        sumOfCutsEqualToWorkingRestDuration(violations, workingRestDuration, workingRestDurationCuts);

        return violations;
    }

    private boolean isSumOfCutsEqualsToAValue(Integer value, List<Integer> cuts) {
        if (cuts == null || cuts.isEmpty()) {
            return true;
        }

        if (value == null) {
            return false;
        }

        Integer sum = cuts.stream().mapToInt(Integer::intValue).sum();
        return sum.equals(value);
    }

    private void sumOfCutsEqualToDrivingPauseDuration(List<ValidationViolation> violations, Integer drivingPauseDuration, List<Integer> drivingPauseDurationCuts) {
        if (!isSumOfCutsEqualsToAValue(drivingPauseDuration, drivingPauseDurationCuts)) {
            violations.add(PlanningUpdateViolation.of("legislationProfile.sumOfCutsEqualToDrivingPauseDuration"));
        }
    }

    private void sumOfCutsEqualToWorkingPauseDuration(List<ValidationViolation> violations, Integer workingPauseDuration, List<Integer> workingPauseDurationCuts) {
        if (!isSumOfCutsEqualsToAValue(workingPauseDuration, workingPauseDurationCuts)) {
            violations.add(PlanningUpdateViolation.of("legislationProfile.sumOfCutsEqualToWorkingPauseDuration"));
        }
    }

    private void sumOfCutsEqualToDrivingRestDuration(List<ValidationViolation> violations, Integer drivingRestDuration, List<Integer> drivingRestDurationCuts) {
        if (!isSumOfCutsEqualsToAValue(drivingRestDuration, drivingRestDurationCuts)) {
            violations.add(PlanningUpdateViolation.of("legislationProfile.sumOfCutsEqualToDrivingRestDuration"));
        }
    }

    private void sumOfCutsEqualToWorkingRestDuration(List<ValidationViolation> violations, Integer workingRestDuration, List<Integer> workingRestDurationCuts) {
        if (!isSumOfCutsEqualsToAValue(workingRestDuration, workingRestDurationCuts)) {
            violations.add(PlanningUpdateViolation.of("legislationProfile.sumOfCutsEqualToWorkingRestDuration"));
        }
    }

}
