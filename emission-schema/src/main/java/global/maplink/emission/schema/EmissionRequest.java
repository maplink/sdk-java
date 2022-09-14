package global.maplink.emission.schema;

import lombok.*;

import java.math.BigDecimal;

import static lombok.AccessLevel.PRIVATE;

@Data
@Builder
@RequiredArgsConstructor
@NoArgsConstructor(force = true, access = PRIVATE)
public final class EmissionRequest {
    private final Source source;
    private final Fuel fuelType;
    private final BigDecimal averageConsumption;
    private final BigDecimal fuelPrice;
    private final Integer totalDistance;
}
