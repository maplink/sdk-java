package global.maplink.planning.schema.problem;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

import static lombok.AccessLevel.PRIVATE;

@Data
@Builder
@AllArgsConstructor(access = PRIVATE)
@NoArgsConstructor(force = true)
public class IncompabilityRelationship {

    private final String name;
    private final String incompabilityGroup1;
    private final String incompabilityGroup2;
    private final IncompatibilityType type;
    private final List<String> vehicles;
}
