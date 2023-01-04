package global.maplink.freight.schema;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

import static lombok.AccessLevel.PRIVATE;

@Data
@Builder
@RequiredArgsConstructor(staticName = "of")
@NoArgsConstructor(force = true, access = PRIVATE)
public class FreightCalculationResponse {
    private final String source;
    private final Map<OperationType, Map<Integer, Map<GoodsType, FreightCalculationResult>>> results;
    private final BigDecimal minimumFreight;
    private final BigDecimal minimumFreightWithCosts;

    public Optional<FreightCalculationResult> getResult(OperationType operationType, Integer axis, GoodsType goodsType) {
        return Optional.ofNullable(results)
                .map(byKey(operationType))
                .map(byKey(axis))
                .map(byKey(goodsType));
    }

    private <T, R> Function<Map<T, R>, R> byKey(T key) {
        return (map) -> map.get(key);
    }
}
