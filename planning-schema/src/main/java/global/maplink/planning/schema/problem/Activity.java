package global.maplink.planning.schema.problem;

import java.util.ArrayList;
import java.util.List;

public class Activity {

    private String activity;

    private TimeWindow timeWindow;

    private String type;

    private String site;

    private Integer fixedTimeSite;

    private Double volume;

    private Double weight;

    private List<String> operations;

    private String arrivalSite;

    private String departureSite;

    private Integer distance;

    private Integer nominalDuration;

    private String positioningType;

    private List<CompartmentSolutionGroup> operationCompartments;

    public List<String> getOperations() {

        if (this.operations == null) {
            return new ArrayList<>();
        }

        return this.operations;
    }
}
