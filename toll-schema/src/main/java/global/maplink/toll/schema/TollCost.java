package global.maplink.toll.schema;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.util.Map;

@Data
@Builder
@RequiredArgsConstructor
@NoArgsConstructor(force = true)
public class TollCost {

    private final String currency;
    private final Map<Billing, Double> prices;
    private final TollVehicleType vehicleType;
}