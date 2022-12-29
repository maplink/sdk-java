package global.maplink.toll.async;

import global.maplink.MapLinkSDK;
import global.maplink.env.Environment;
import global.maplink.toll.schema.Billing;
import global.maplink.toll.schema.request.LegRequest;
import global.maplink.toll.schema.request.TollCalculationRequest;
import global.maplink.toll.schema.result.TollCalculationResult;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

import static java.util.Arrays.asList;

public interface TollAsyncAPI {

    default CompletableFuture<TollCalculationResult> calculate(LegRequest... legs) {
        return calculate(asList(legs));
    }

    default CompletableFuture<TollCalculationResult> calculate(Billing billing, LegRequest... legs) {
        return calculate(billing, asList(legs));
    }

    default CompletableFuture<TollCalculationResult> calculate(List<LegRequest> legs) {
        return calculate(
                TollCalculationRequest.builder()
                        .legs(legs)
                        .build()
        );
    }

    default CompletableFuture<TollCalculationResult> calculate(Billing billing, List<LegRequest> legs) {
        return calculate(
                TollCalculationRequest.builder()
                        .billing(billing)
                        .legs(legs)
                        .build()
        );
    }

    CompletableFuture<TollCalculationResult> calculate(TollCalculationRequest request);

    static TollAsyncAPI getInstance() {
        return getInstance(null);
    }

    static TollAsyncAPI getInstance(Environment environment) {
        MapLinkSDK sdk = MapLinkSDK.getInstance();
        return new TollAsyncApiImpl(
                Optional.ofNullable(environment).orElse(sdk.getEnvironment()),
                sdk.getHttp(),
                sdk.getJsonMapper(),
                sdk.getTokenProvider(),
                sdk.getCredentials()
        );
    }

}
