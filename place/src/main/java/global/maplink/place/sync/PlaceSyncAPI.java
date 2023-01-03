package global.maplink.place.sync;

import global.maplink.env.Environment;
import global.maplink.place.async.PlaceAsyncAPI;
import global.maplink.place.schema.PlaceRouteRequest;
import global.maplink.place.schema.PlaceRouteResponse;

public interface PlaceSyncAPI {

    PlaceRouteResponse calculate(PlaceRouteRequest request);

    static PlaceSyncAPI getInstance() {
        return new PlaceSyncApiImpl(PlaceAsyncAPI.getInstance());
    }

    static PlaceSyncAPI getInstance(Environment environment) {
        return new PlaceSyncApiImpl(PlaceAsyncAPI.getInstance(environment));
    }
}
