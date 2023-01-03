package global.maplink.place.async;

import global.maplink.MapLinkSDK;
import global.maplink.env.Environment;
import global.maplink.place.schema.PlaceRouteRequest;
import global.maplink.place.schema.PlaceRouteResponse;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;

public interface PlaceAsyncAPI {

    CompletableFuture<PlaceRouteResponse> calculate(PlaceRouteRequest request);

    static PlaceAsyncAPI getInstance() {
        return getInstance(null);
    }

    static PlaceAsyncAPI getInstance(Environment environment) {
        MapLinkSDK sdk = MapLinkSDK.getInstance();
        return new PlaceAsyncApiImpl(
                Optional.ofNullable(environment).orElse(sdk.getEnvironment()),
                sdk.getHttp(),
                sdk.getJsonMapper(),
                sdk.getTokenProvider(),
                sdk.getCredentials()
        );
    }
}
