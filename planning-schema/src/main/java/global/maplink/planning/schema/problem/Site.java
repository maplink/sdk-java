package global.maplink.planning.schema.problem;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor(force = true)
@Builder
public class Site {

    private final String site;

    private final Coordinates coordinates;

    private final String logisticConstraints;

    private final List<String> logisticZones;
}
