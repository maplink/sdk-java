package global.maplink.planning.schema.solution;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public enum ActivityType {

    LOADING,
    DRIVING,
    DELIVERY,
    COLLECTION,
    ROUTE_START,
    ROUTE_END,
    WAITING;
}
