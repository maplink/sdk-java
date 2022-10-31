package gloabl.maplink.toll.schema;

import lombok.RequiredArgsConstructor;

import java.util.Arrays;

public enum TollDirection {
    EAST,
    NORTHEAST,
    NORTH,
    NORTHWEST,
    WEST,
    SOUTHWEST,
    SOUTH,
    SOUTHEAST;

    public static String[] arrayOfNames() {
        return (String[]) Arrays.stream(values()).map(Enum::name).toArray(String[]::new);
    }
}
