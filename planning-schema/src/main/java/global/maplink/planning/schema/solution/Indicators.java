package global.maplink.planning.schema.solution;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

import static lombok.AccessLevel.PRIVATE;

@Data
@Builder
@AllArgsConstructor(access = PRIVATE)
@NoArgsConstructor(force = true)
public class Indicators {

    private final Integer totalServiceTime;

    private final Integer totalDeliveringTime;

    private final Integer dayWorkingTotalTime;

    private final Integer nightWorkingTotalTime;

    private final Integer totalUnloadingTime;

    private final Integer totalWorkingTime;

    private final Integer totalCollectingTime;

    private final Integer timeWindowNumber;

    private final Integer totalDrivingTime;

    private final Integer totalLoadingTime;

    private final Integer totalTime;

    private final Integer totalDistance;

    private final Double averageOccupancyRateVolume;

    private final Double averageOccupancyRateWeight;

    private final Integer rejectOperationsNumber;

    private final Integer totalWaitingTime;

    private final Integer totalRestTime;

    private final Integer routesNumber;

    private final BigDecimal tollCosts;
    
}
