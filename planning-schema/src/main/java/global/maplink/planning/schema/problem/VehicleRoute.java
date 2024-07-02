package global.maplink.planning.schema.problem;

import lombok.*;

import java.util.List;

import static lombok.AccessLevel.PRIVATE;

@Data
@Builder
@AllArgsConstructor(access = PRIVATE)
@NoArgsConstructor(force = true)
public class VehicleRoute {

    @Singular
    private final List<Route> routes;
    private final String vehicle;
    private final AvailablePeriod period;
}
