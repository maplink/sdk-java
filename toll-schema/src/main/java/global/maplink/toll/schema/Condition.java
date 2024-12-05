package global.maplink.toll.schema;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Condition {

    @Builder.Default
    private TollConditionBillingType billingType = TollConditionBillingType.NORMAL;

    @Builder.Default
    private TollConditionPeriod period = TollConditionPeriod.NORMAL;

}