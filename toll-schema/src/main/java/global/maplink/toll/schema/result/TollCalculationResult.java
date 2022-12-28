package global.maplink.toll.schema.result;

import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor(force = true)
@Builder
public class TollCalculationResult {
    @Singular
    private final List<LegResult> legs;
    private final BigDecimal totalCost;
}
