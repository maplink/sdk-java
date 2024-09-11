package global.maplink.trip.schema.v2.features.turnByTurn;


import global.maplink.domain.MaplinkPoints;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import static lombok.AccessLevel.PRIVATE;

@Data
@Builder
@RequiredArgsConstructor
@NoArgsConstructor(force = true, access = PRIVATE)
public class TurnByTurnInstruction {

    private final Double distance;
    private final Instructions type;
    private final MaplinkPoints points;
    private final String text;
    private final int duration;
    private final int exitNumber;
}
