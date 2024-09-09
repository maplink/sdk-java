package global.maplink.trip.schema.v2.features.turnByTurn;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Instructions {

    U_TURN,
    LEFT_U_TURN,
    KEEP_LEFT,
    LEAVE_ROUNDABOUT,
    TURN_SHARP_LEFT,
    TURN_LEFT,
    TURN_SLIGHT_LEFT,
    CONTINUE_ON_STREET,
    TURN_SLIGHT_RIGHT,
    TURN_RIGHT,
    TURN_SHARP_RIGHT,
    LAST_POINT,
    VIA_POINT,
    ROUNDABOUT,
    KEEP_RIGHT,
    RIGHT_U_TURN,
    UNKNOWN;

}