package global.maplink.planning.schema.problem;

import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor(force = true)
@Builder
public class Operation {

    private final String id;

    private final String group;

    private final Double weight;

    private final Double volume;

    private final Double quantity;

    private final String product;

    private final String type;

    private final Integer priority;

    private final String depotSize;

    private final String customerSite;

    @Singular
    private final List<TimeWindow> customerTimeWindows;

    @Singular
    private final List<TimeWindow> depotTimeWindows;

    private final String preAllocatedVehicleName;

    private final Integer depotHandlingDuration;

    private final String status;

    private final Boolean depotTimeWindowBlocked;

    private final Boolean customerTimeWindowBlocked;

    private final List<String> characteristics;


}
