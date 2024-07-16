package global.maplink.planning.schema.solution;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public enum PositioningType {

    TO_BE_DETERMINED,
    TO_OPTIMIZE,
    NOT_REVIEWABLE,
    REALIZED,
    TO_BE_DETERMINED_AFTER_REALIZED,
    TO_OPTIMIZE_AFTER_REALIZED;
}
