package global.maplink.toll.schema;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static java.util.Collections.emptyList;

@Data
@Builder
@RequiredArgsConstructor
@NoArgsConstructor(force = true)
public class SegmentCosts {

    private final String entryGantryId;
    private final List<TollCost> costs = emptyList();
    private final  List<TollCondition> conditions;
    private final List<TollServiceType> serviceTypes = emptyList();
}
