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

public class IncompabilityRelationship {

    private final String name;

    private final String incompabilityGroup1;

    private final String incompabilityGroup2;

    private final String type;

    private final List<String> vehicles;
}
