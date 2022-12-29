package global.maplink.toll.schema.result;

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
public class LegResult {
    private final List<CalculationDetail> tolls;
    private final BigDecimal legTotalCost;
}
