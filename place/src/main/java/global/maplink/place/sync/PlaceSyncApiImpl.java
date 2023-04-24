package global.maplink.place.sync;

import global.maplink.place.async.PlaceAsyncAPI;
import global.maplink.place.schema.*;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;

import static global.maplink.helpers.FutureHelper.await;
import static lombok.AccessLevel.PROTECTED;

@RequiredArgsConstructor(access = PROTECTED)
public class PlaceSyncApiImpl implements PlaceSyncAPI {
    private final PlaceAsyncAPI delegate;

    @Override
    public PlaceRouteResponse calculate(PlaceRouteRequest request) {
        return await(delegate.calculate(request));
    }

    @Override
    public void create(CreatePlaceRequest request) {
        await(delegate.create(request));
    }

    @Override
    public void create(Place place) {
        await(delegate.create(place));
    }

    @Override
    public List<String> listAllStates(ListAllStatesRequest request) {
        return await(delegate.listAllStates(request));
    }

    @Override
    public List<String> listAllCities(ListAllCitiesRequest request) {
        return await(delegate.listAllCities(request));
    }

    @Override
    public List<String> listAllDistricts(ListAllDistrictsRequest request) {
        return await(delegate.listAllDistricts(request));
    }

    @Override
    public PlacePageResult listAll(ListAllPlacesRequest request) {
        return await(delegate.listAll(request));
    }

    @Override
    public PlacePageResult listAll(int limit, int offset) {
        return await(delegate.listAll(limit, offset));
    }

    @Override
    public PlacePageResult listAll() {
        return await(delegate.listAll());
    }

    @Override
    public Optional<Place> getByOriginId(PlaceByOriginIdRequest request) {
        return await(delegate.getByOriginId(request));
    }

    @Override
    public Optional<Place> getByOriginId(String originId) {
        return await(delegate.getByOriginId(originId));
    }
}
