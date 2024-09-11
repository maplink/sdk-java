package global.maplink.trip.schema.v2.solution;

import global.maplink.domain.MaplinkPoints;
import global.maplink.geocode.schema.Address;
import global.maplink.toll.schema.result.LegResult;
import global.maplink.trip.schema.v2.features.turnByTurn.TurnByTurnInstruction;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static lombok.AccessLevel.PRIVATE;

@Data
@Builder
@RequiredArgsConstructor
@NoArgsConstructor(force = true, access = PRIVATE)
public class SolutionLeg {
    private final Long distance;
    private final Long nominalDuration;
    private final Double averageSpeed;
    private final MaplinkPoints points;
    private final List<TurnByTurnInstruction> turnByTurn;
    private final Address firstPointAddress;
    private final global.maplink.place.schema.LegResult placeCalculation;
    private final LegResult tollCalculation;
}
