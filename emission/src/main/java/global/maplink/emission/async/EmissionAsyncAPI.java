package global.maplink.emission.async;

import global.maplink.MapLinkSDK;
import global.maplink.emission.schema.EmissionRequest;
import global.maplink.emission.schema.EmissionResponse;
import global.maplink.emission.schema.Fuel;
import global.maplink.emission.schema.Source;
import global.maplink.env.Environment;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

public interface EmissionAsyncAPI {

    default CompletableFuture<EmissionResponse> calculate(
            Source source,
            Fuel fuelType,
            BigDecimal averageComsumption,
            BigDecimal fuelPrice,
            Integer distance
    ) {
        return calculate(EmissionRequest.builder()
                .source(source)
                .fuelType(fuelType)
                .averageConsumption(averageComsumption)
                .fuelPrice(fuelPrice)
                .totalDistance(distance)
                .build());
    }

    CompletableFuture<EmissionResponse> calculate(EmissionRequest request);

    static EmissionAsyncAPI getInstance() {
        return getInstance(null);
    }

    static EmissionAsyncAPI getInstance(Environment environment) {
        MapLinkSDK sdk = MapLinkSDK.getInstance();
        return new EmissionAsyncApiImpl(
                Optional.ofNullable(environment).orElse(sdk.getEnvironment()),
                sdk.getHttp(),
                sdk.getJsonMapper(),
                sdk.getTokenProvider(),
                sdk.getCredentials()
        );
    }
}
