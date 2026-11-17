package global.maplink.trip.schema.v1.payload;

public enum AvoidanceType {
    TUNNELS,
    BRIDGES,
    FERRIES,
    FRONTIERS,
    /**
     * @deprecated Will be removed in a future major version. Currently equivalent to avoiding toll roads.
     */
    @Deprecated
    TOLL_ROADS,
    UNPAVED,
    RURAL,
    RESIDENTIAL,
    CITY
}
