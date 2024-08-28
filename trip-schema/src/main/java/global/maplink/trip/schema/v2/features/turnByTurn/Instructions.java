package global.maplink.trip.schema.v2.features.turnByTurn;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import static java.lang.Integer.MIN_VALUE;
import static java.util.Arrays.stream;

@Getter
@RequiredArgsConstructor
public enum Instructions {

    U_TURN(-98),
    LEFT_U_TURN(-8),
    KEEP_LEFT(-7),
    LEAVE_ROUNDABOUT(-6),
    TURN_SHARP_LEFT(-3),
    TURN_LEFT(-2),
    TURN_SLIGHT_LEFT(-1),
    CONTINUE_ON_STREET(0),
    TURN_SLIGHT_RIGHT(1),
    TURN_RIGHT(2),
    TURN_SHARP_RIGHT(3),
    LAST_POINT(4),
    VIA_POINT(5),
    ROUNDABOUT(6),
    KEEP_RIGHT(7),
    RIGHT_U_TURN(8),
    UNKNOWN(MIN_VALUE);

    private final int sign;

    public static Instructions fromInstruction(int sign) {
       return stream(Instructions.values())
               .filter(type -> type.sign == sign)
               .findFirst()
               .orElse(UNKNOWN);
    }
}