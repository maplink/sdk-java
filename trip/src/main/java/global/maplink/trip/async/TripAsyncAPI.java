package global.maplink.trip.async;

import global.maplink.MapLinkSDK;
import global.maplink.env.Environment;
import global.maplink.trip.schema.v2.problem.TripCalculateRequest;
import global.maplink.trip.schema.v2.solution.TripSolution;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;

import static global.maplink.MapLinkServiceRequestAsyncRunner.proxyFor;

public interface TripAsyncAPI {

    CompletableFuture<TripSolution> calculate(TripCalculateRequest request);

    static TripAsyncAPI getInstance() {
        return getInstance(null);
    }

    static TripAsyncAPI getInstance(Environment environment) {
        MapLinkSDK sdk = MapLinkSDK.getInstance();
        return proxyFor(
                TripAsyncAPI.class,
                Optional.ofNullable(environment).orElse(sdk.getEnvironment()),
                sdk.getHttp(),
                sdk.getJsonMapper(),
                sdk.getTokenProvider(),
                sdk.getCredentials()
        );
    }
}
