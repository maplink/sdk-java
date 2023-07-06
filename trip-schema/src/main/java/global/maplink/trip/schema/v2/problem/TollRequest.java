package global.maplink.trip.schema.v2.problem;

import global.maplink.commons.TransponderOperator;
import global.maplink.toll.schema.Billing;
import global.maplink.toll.schema.TollVehicleType;
import lombok.*;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static lombok.AccessLevel.PRIVATE;

@Data
@Builder
@RequiredArgsConstructor
@NoArgsConstructor(force = true, access = PRIVATE)
public class TollRequest {
    private final TollVehicleType vehicleType;
    @Builder.Default
    private final Billing billing = Billing.DEFAULT;
    @Builder.Default
    private final Set<TransponderOperator> transponderOperators = new HashSet<>(Collections.singletonList(TransponderOperator.SEM_PARAR));
    private final List<LegVariableAxles> variableAxles;

    public void validate(final List<String> errors, final List<SitePoint> sites) {
        var variableAxlesValidator = new VariableAxlesValidator();
        variableAxlesValidator.checkNotNull(errors, "vehicleType", vehicleType);
        variableAxlesValidator.checkNotNull(errors, "billing", billing);
        variableAxlesValidator.checkNotNull(errors, "transponderOperators", transponderOperators);
        variableAxlesValidator.checkVariableAxles(errors, sites, variableAxles);
    }
}
