package global.maplink.emission.async;

import global.maplink.MapLinkSDK;
import global.maplink.emission.schema.EmissionRequest;
import global.maplink.emission.schema.EmissionResponse;
import global.maplink.emission.schema.FractionedEmission;
import global.maplink.env.Environment;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

import static global.maplink.MapLinkServiceRequestAsyncRunner.proxyFor;

public interface EmissionAsyncAPI {

    default CompletableFuture<EmissionResponse> calculate(
            String source,
            String fuelType,
            BigDecimal autonomy,
            BigDecimal averageConsumption,
            BigDecimal fuelPrice,
            Integer distance,
            List<FractionedEmission> fractionedEmissions
    ) {
        return calculate(EmissionRequest.builder()
                .source(source)
                .fuelType(fuelType)
                .autonomy(autonomy)
                .averageConsumption(averageConsumption)
                .fuelPrice(fuelPrice)
                .totalDistance(distance)
                .fractionedEmissions(fractionedEmissions)
                .build());
    }

    CompletableFuture<EmissionResponse> calculate(EmissionRequest request);

    static EmissionAsyncAPI getInstance() {
        return getInstance(null);
    }

    static EmissionAsyncAPI getInstance(Environment environment) {
        MapLinkSDK sdk = MapLinkSDK.getInstance();
        return proxyFor(
                EmissionAsyncAPI.class,
                Optional.ofNullable(environment).orElse(sdk.getEnvironment()),
                sdk
        );
    }
}
