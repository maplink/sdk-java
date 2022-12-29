package global.maplink.toll.sync;

import global.maplink.env.Environment;
import global.maplink.toll.async.TollAsyncAPI;
import global.maplink.toll.schema.Billing;
import global.maplink.toll.schema.request.LegRequest;
import global.maplink.toll.schema.request.TollCalculationRequest;
import global.maplink.toll.schema.result.TollCalculationResult;

import java.util.List;

import static java.util.Arrays.asList;

public interface TollSyncAPI {

    default TollCalculationResult calculate(LegRequest... legs) {
        return calculate(asList(legs));
    }

    default TollCalculationResult calculate(Billing billing, LegRequest... legs) {
        return calculate(billing, asList(legs));
    }

    default TollCalculationResult calculate(List<LegRequest> legs) {
        return calculate(
                TollCalculationRequest.builder()
                        .legs(legs)
                        .build()
        );
    }

    default TollCalculationResult calculate(Billing billing, List<LegRequest> legs) {
        return calculate(
                TollCalculationRequest.builder()
                        .billing(billing)
                        .legs(legs)
                        .build()
        );
    }

    TollCalculationResult calculate(TollCalculationRequest request);

    static TollSyncAPI getInstance() {
        return new TollSyncApiImpl(TollAsyncAPI.getInstance());
    }

    static TollSyncAPI getInstance(Environment environment) {
        return new TollSyncApiImpl(TollAsyncAPI.getInstance(environment));
    }

}
