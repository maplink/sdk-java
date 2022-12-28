package global.maplink.toll.schema.request;

import global.maplink.toll.schema.Billing;
import global.maplink.toll.schema.Source;
import lombok.*;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor(force = true)
public class TollCalculationRequest {

    @Singular
    private final List<LegRequest> legs;

    @Builder.Default
    private final Source source = Source.DEFAULT;

    @Builder.Default
    private final Billing billing = Billing.DEFAULT;

}
