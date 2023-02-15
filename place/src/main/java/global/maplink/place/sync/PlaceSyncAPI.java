package global.maplink.place.sync;

import global.maplink.env.Environment;
import global.maplink.place.async.PlaceAsyncAPI;
import global.maplink.place.schema.*;

import java.util.Optional;

public interface PlaceSyncAPI {

    PlaceRouteResponse calculate(PlaceRouteRequest request);

    void create(CreatePlaceRequest request);

    void create(Place place);

    PlacePageResult listAll(ListAllPlacesRequest request);

    PlacePageResult listAll(int limit, int offset);

    PlacePageResult listAll();

    Optional<Place> getByOriginId(PlaceByOriginIdRequest request);

    Optional<Place> getByOriginId(String originId);

    static PlaceSyncAPI getInstance() {
        return new PlaceSyncApiImpl(PlaceAsyncAPI.getInstance());
    }

    static PlaceSyncAPI getInstance(Environment environment) {
        return new PlaceSyncApiImpl(PlaceAsyncAPI.getInstance(environment));
    }
}
