package global.maplink.planning.schema.problem;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public enum OptimizationType {

    /**
     * It is used to global calculation
     */
    CUSTOM,

    /**
     * It is used to interactive manipulation
     */
    FIXED_SOLUTION;
}
