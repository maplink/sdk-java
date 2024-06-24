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
public class RouteStatus {

    public static final String FREE = "FREE";

    public static final String LOCKED = "LOCKED";

    public static final String VALIDATED = "VALIDATED";
}
