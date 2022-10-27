package global.maplink.freight.schema;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

import static lombok.AccessLevel.PRIVATE;

@Data
@Builder
@RequiredArgsConstructor(staticName = "of")
@NoArgsConstructor(force = true, access = PRIVATE)

public class FreightCalculationRequest {
    private final Set<OperationType> operationType;
    private final Set<GoodsType> goodsType;
    private final Set<Integer> axis;
    private final List<AdditionalCosts> otherCosts;
    private final BigDecimal distance;
    private final LocalDate date;
    private final boolean roundTrip;
    private final boolean backEmpty;
}
