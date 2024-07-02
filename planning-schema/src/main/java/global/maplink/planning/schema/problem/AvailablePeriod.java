package global.maplink.planning.schema.problem;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PRIVATE;

@Data
@Builder
@AllArgsConstructor(access = PRIVATE)
@NoArgsConstructor(force = true)
public class AvailablePeriod {

    private final TimeWindow timeWindow;
    private final String departureSite;
    private final String arrivalSite;
    private final Integer maxRoutesNumber;
    private final Integer maxWorkingTime;
    private final Integer maxDrivingTime;
}
