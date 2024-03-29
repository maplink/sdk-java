package global.maplink.emission.schema;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

import static lombok.AccessLevel.PRIVATE;

@Data
@Builder
@RequiredArgsConstructor
@NoArgsConstructor(force = true, access = PRIVATE)
public final class EmissionResponse {
    private final String fuelType;
    private final String source;
    private final BigDecimal fuelConsumed;
    private final BigDecimal totalFuelPrice;
    private final BigDecimal totalEmission;
    private final List<FractionedEmissionResponse> fractionedEmissionResponses;
}
