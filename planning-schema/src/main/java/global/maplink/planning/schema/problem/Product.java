package global.maplink.planning.schema.problem;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor(force = true)
@Builder
public class Product {

    private final String name;

    private final String type;

    private final String incompabilityGroup;

    private final String[] packagings;
}
