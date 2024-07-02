package global.maplink.planning.schema.solution;

import lombok.*;

import java.util.Set;

import static lombok.AccessLevel.PRIVATE;

@Data
@Builder
@AllArgsConstructor(access = PRIVATE)
@NoArgsConstructor(force = true)
public class PendingTasks {

    private final Set<String> trip;
}
