package global.maplink.emission.schema;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;

import static lombok.AccessLevel.PRIVATE;

@Data
@Builder
@RequiredArgsConstructor
@NoArgsConstructor(force = true, access = PRIVATE)
public class FractionedEmissionResponse {
    private final String name;
    private final BigDecimal fuelConsumed;
    private final BigDecimal totalFuelPrice;
    private final BigDecimal totalEmission;
}
