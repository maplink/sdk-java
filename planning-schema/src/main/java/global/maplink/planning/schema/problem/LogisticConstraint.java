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
public class LogisticConstraint {

    private final String name;
    private final Integer siteUnloadingFixedTime;
    private final Integer siteLoadingFixedTime;
    private final Integer loadingMaxSize;
    private final Integer unloadingMaxSize;
    private final String loadingPositionInTimeWindow;
    private final String unloadingPositionInTimeWindow;
    private final String loadingPositionInRoute;
    private final String unloadingPositionInRoute;
    private final Double loadingTimeFlow;
    private final Double unloadingTimeFlow;
}
