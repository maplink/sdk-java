package global.maplink.planning.schema.problem;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(force = true)
public enum CalculationMode {
    THE_FASTEST,
    THE_SHORTEST;

    private CalculationMode(){

    }
}
