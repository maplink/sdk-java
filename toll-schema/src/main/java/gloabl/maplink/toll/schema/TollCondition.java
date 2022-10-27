package gloabl.maplink.toll.schema;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.util.Set;

import static lombok.AccessLevel.PRIVATE;

@Data
@Builder
@RequiredArgsConstructor
@NoArgsConstructor(force = true, access = PRIVATE)
public class TollCondition {
    private final Set<DayOfWeek> daysOfWeek;
    private final Set<TollConditionPeriod> periods;
    private final Set<TollConditionBillingType> billingsType;
    private final Set<String> timesWindow;
    private final Set<String> tags;
    private final Set<TollVehicleType> vehicleTypes;
    private final Set<String> routes;
    private final Set<TollCondition> subConditions;
    private final BigDecimal value;
}
