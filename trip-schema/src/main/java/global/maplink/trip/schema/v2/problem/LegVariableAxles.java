package global.maplink.trip.schema.v2.problem;

import global.maplink.toll.schema.TollVehicleType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LegVariableAxles {
    private String fromSiteId;
    private String toSiteId;
    private TollVehicleType newVehicleType;
}
