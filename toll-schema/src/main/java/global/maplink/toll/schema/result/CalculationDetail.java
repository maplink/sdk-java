package global.maplink.toll.schema.result;

import global.maplink.domain.MaplinkPoint;
import global.maplink.toll.schema.*;
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
public class CalculationDetail {
    private final String id;
    private final String name;
    private final String address;
    private final String city;
    private final State state;
    private final String country;
    private final String concession;
    private final TollDirection direction;
    private final MaplinkPoint coordinates;
    private final List<TollServiceType> serviceTypes;
    private final BigDecimal price;
    private final List<TollCondition> conditions;
    private final List<SegmentCosts> segmentCosts;
    private final boolean allowMultiplier;
}
