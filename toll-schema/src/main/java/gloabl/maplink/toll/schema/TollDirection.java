package gloabl.maplink.toll.schema;

import java.util.Arrays;

public enum TollDirection {
    EAST(6.283185307179586),
    NORTHEAST(0.7853981633974483),
    NORTH(1.5707963267948966),
    NORTHWEST(2.356194490192345),
    WEST(Math.PI),
    SOUTHWEST(3.9269908169872414),
    SOUTH(4.71238898038469),
    SOUTHEAST(5.497787143782138);

    private Double angle;

    public static String[] arrayOfNames() {
        return (String[]) Arrays.stream(values()).map(Enum::name).toArray((x$0) -> {
            return new String[x$0];
        });
    }

    public Double getAngle() {
        return this.angle;
    }

    private TollDirection(final Double angle) {
        this.angle = angle;
    }
}
