package global.maplink.planning.schema.problem;

import lombok.*;

import java.util.List;

import static lombok.AccessLevel.PRIVATE;

@Data
@Builder
@AllArgsConstructor(access = PRIVATE)
@NoArgsConstructor(force = true)

public class Route {

    private final String id;

    @Singular
    private final List<Activity> activities;

    private final String status;

    private final List<ViolationConstraint> violationConstraints;

    private final String compartmentConfiguration;

    private final String tripId;
}
