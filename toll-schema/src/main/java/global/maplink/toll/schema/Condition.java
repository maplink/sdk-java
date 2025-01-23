package global.maplink.toll.schema;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor(force = true)
@AllArgsConstructor
public class Condition {

    private final TollConditionBillingType billingType;

    private final TollConditionPeriod period;

}