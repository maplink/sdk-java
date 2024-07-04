package global.maplink.planning.schema.problem;

import global.maplink.planning.schema.commons.CompartmentSolutionGroup;
import global.maplink.planning.schema.solution.ActivityType;
import global.maplink.planning.schema.solution.PositioningType;
import global.maplink.planning.schema.solution.SequenceType;
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
public class Activity {

    private final ActivityType activity;
    private final TimeWindow timeWindow;
    private final SequenceType type;
    private final String site;
    private final Integer fixedTimeSite;
    private final Double volume;
    private final Double weight;
    private final List<String> operations;
    private final String arrivalSite;
    private final String departureSite;
    private final Integer distance;
    private final Integer nominalDuration;
    private final PositioningType positioningType;
    private final List<CompartmentSolutionGroup> operationCompartments;
}
