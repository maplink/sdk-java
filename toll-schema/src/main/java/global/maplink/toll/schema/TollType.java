package global.maplink.toll.schema;

public enum TollType {
    @Deprecated
    GATE,
    @Deprecated
    PORTICO,
    TOLL_BOOTH,
    TOLL_GANTRY,
    MUNICIPAL_TOLL_BOOTH,
    ENTRY_GANTRY,
    EXIT_GANTRY;
}