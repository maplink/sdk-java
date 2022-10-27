package global.maplink.trip.schema.solution;

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
public class TollConditionDTO_deprecated {
    private final Set<DayOfWeek> daysOfWeek;
    private final Set<String> periods;
    private final Set<String> billingTypes;
    private final Set<String> timesWindow;
    private final Set<String> tags;
    private final Set<String> vehicleTypes;
    private final Set<String> routes;
    private final Set<TollConditionDTO_deprecated> subConditions;
    private final BigDecimal value;
}
