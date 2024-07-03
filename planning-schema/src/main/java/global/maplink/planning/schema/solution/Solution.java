package global.maplink.planning.schema.solution;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import global.maplink.planning.schema.problem.VehicleRoute;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_EMPTY;
import static com.fasterxml.jackson.annotation.JsonProperty.Access.WRITE_ONLY;
import static lombok.AccessLevel.PRIVATE;

@Data
@Builder
@AllArgsConstructor(access = PRIVATE)
@NoArgsConstructor(force = true)
public class Solution {

    private final String clientId;
    private final List<VehicleRoute> vehicleRoutes;
    private final List<String> rejectOperations;
    @JsonIgnore
    private final List<PossibleCauseRejectGroup> possibleCauseOfRejectOperationsGroup;
    private final Indicators indicators;
    @JsonProperty(access = WRITE_ONLY)
    @JsonInclude(NON_EMPTY)
    private final PendingTasks pendingTasks;
    @JsonIgnore
    private final Date createdAt;
}
