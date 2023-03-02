package global.maplink.freight.async;

import global.maplink.MapLinkSDK;
import global.maplink.env.Environment;
import global.maplink.freight.schema.FreightCalculationRequest;
import global.maplink.freight.schema.FreightCalculationResponse;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;

import static global.maplink.MapLinkServiceRequestAsyncRunner.proxyFor;

public interface FreightAsyncAPI {

    CompletableFuture<FreightCalculationResponse> calculate(FreightCalculationRequest request);

    static FreightAsyncAPI getInstance() {
        return getInstance(null);
    }

    static FreightAsyncAPI getInstance(Environment environment) {
        MapLinkSDK sdk = MapLinkSDK.getInstance();
        return proxyFor(
                FreightAsyncAPI.class,
                Optional.ofNullable(environment).orElse(sdk.getEnvironment()),
                sdk
        );
    }
}
