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
public class PositioningType {

    public static final String TO_BE_DETERMINED = "TO_BE_DETERMINED";

    public static final String TO_OPTIMIZE = "TO_OPTIMIZE";

    public static final String NOT_REVIEWABLE = "NOT_REVIEWABLE";

    public static final String REALIZED = "REALIZED";

    public static final String TO_BE_DETERMINED_AFTER_REALIZED = "TO_BE_DETERMINED_AFTER_REALIZED";

    public static final String TO_OPTIMIZE_AFTER_REALIZED = "TO_OPTIMIZE_AFTER_REALIZED";
}
