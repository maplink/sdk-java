package global.maplink.planning.schema.solution;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PRIVATE;

@Data
@Builder
@AllArgsConstructor(access = PRIVATE)
@NoArgsConstructor(force = true)

public class ActivityType {

    public static final String LOADING = "LOADING";

    public static final String DRIVING = "DRIVING";

    public static final String DELIVERY = "DELIVERY";

    public static final String COLLECTION = "COLLECTION";

    public static final String ROUTE_START = "ROUTE_START";

    public static final String ROUTE_END = "ROUTE_END";

    public static final String WAITING = "WAITING";
}
