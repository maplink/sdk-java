package global.maplink.trip.schema.v2.problem;

import global.maplink.toll.schema.Billing;
import global.maplink.toll.schema.TollVehicleType;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import static lombok.AccessLevel.PRIVATE;

@Data
@Builder
@RequiredArgsConstructor
@NoArgsConstructor(force = true, access = PRIVATE)
public class TollRequest {
    private final TollVehicleType vehicleType;
    private final Billing billing;
}
