package global.maplink.planning.schema.problem;

import global.maplink.freight.schema.FreightCalculationRequest;
import global.maplink.trip.schema.v2.features.crossedBorders.CrossedBordersRequest;
import global.maplink.trip.schema.v2.problem.CalculationMode;
import global.maplink.trip.schema.v2.problem.TollRequest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PRIVATE;

@Data
@Builder
@AllArgsConstructor(access = PRIVATE)
@NoArgsConstructor(force = true)
public class ProblemTrip {

    private static final int MINIMUM_POINTS = 2;
    private final String profileName;
    private final CalculationMode calculationMode;
    private final Long startDate;
    private final TollRequest toll;
    private final CrossedBordersRequest crossedBoarders;
    private final FreightCalculationRequest freight;
}
