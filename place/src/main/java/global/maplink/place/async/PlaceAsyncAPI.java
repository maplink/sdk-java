package global.maplink.place.async;

import global.maplink.MapLinkSDK;
import global.maplink.env.Environment;
import global.maplink.place.schema.*;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;

import static global.maplink.MapLinkServiceRequestAsyncRunner.proxyFor;

public interface PlaceAsyncAPI {

    CompletableFuture<PlaceRouteResponse> calculate(PlaceRouteRequest request);

    CompletableFuture<Void> create(CreatePlaceRequest request);

    default CompletableFuture<Void> create(Place place) {
        return create(new CreatePlaceRequest(place));
    }

    CompletableFuture<PlacePageResult> listAll(ListAllPlacesRequest request);

    default CompletableFuture<PlacePageResult> listAll(int limit, int offset) {
        return listAll(ListAllPlacesRequest.builder().limit(limit).offset(offset).build());
    }

    default CompletableFuture<PlacePageResult> listAll() {
        return listAll(ListAllPlacesRequest.builder().build());
    }

    CompletableFuture<Optional<Place>> getByOriginId(PlaceByOriginIdRequest request);

    default CompletableFuture<Optional<Place>> getByOriginId(String originId) {
        return getByOriginId(PlaceByOriginIdRequest.builder().originId(originId).build());
    }

    static PlaceAsyncAPI getInstance() {
        return getInstance(null);
    }

    static PlaceAsyncAPI getInstance(Environment environment) {
        MapLinkSDK sdk = MapLinkSDK.getInstance();
        return proxyFor(
                PlaceAsyncAPI.class,
                Optional.ofNullable(environment).orElse(sdk.getEnvironment()),
                sdk
        );
    }
}
