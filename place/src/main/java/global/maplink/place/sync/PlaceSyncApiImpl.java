package global.maplink.place.sync;

import global.maplink.place.async.PlaceAsyncAPI;
import global.maplink.place.schema.PlaceRouteRequest;
import global.maplink.place.schema.PlaceRouteResponse;
import lombok.RequiredArgsConstructor;

import static global.maplink.helpers.FutureHelper.await;
import static lombok.AccessLevel.PROTECTED;

@RequiredArgsConstructor(access = PROTECTED)
public class PlaceSyncApiImpl implements PlaceSyncAPI {
    private final PlaceAsyncAPI delegate;

    @Override
    public PlaceRouteResponse calculate(PlaceRouteRequest request) {
        return await(delegate.calculate(request));
    }
}
