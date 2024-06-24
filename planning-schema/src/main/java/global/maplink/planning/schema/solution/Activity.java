package global.maplink.planning.schema.solution;

import global.maplink.planning.schema.problem.CompartmentSolutionGroup;
import global.maplink.planning.schema.problem.TimeWindow;
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

    private final String activity;
    private final TimeWindow timeWindow;
    private final String type;
    private final String site;
    private final Integer fixedTimeSite;
    private final Double volume;
    private final Double weight;
    private final List<String> operations;
    private final String arrivalSite;
    private final String departureSite;
    private final Integer distance;
    private final Integer nominalDuration;
    private final String positioningType;
    private final List<CompartmentSolutionGroup> operationCompartments;
}
