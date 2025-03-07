package global.maplink.geocode.sync.v1;

import global.maplink.geocode.async.v1.GeocodeAsyncAPI;
import global.maplink.geocode.schema.v1.cities.CitiesByStateRequest;
import global.maplink.geocode.schema.v1.crossCities.CrossCitiesRequest;
import global.maplink.geocode.schema.v2.reverse.ReverseBaseRequest;
import global.maplink.geocode.schema.v2.structured.StructuredBaseRequest;
import global.maplink.geocode.schema.v2.suggestions.SuggestionsBaseRequest;
import global.maplink.geocode.schema.v2.suggestions.SuggestionsResult;
import lombok.RequiredArgsConstructor;

import static global.maplink.helpers.FutureHelper.await;
import static lombok.AccessLevel.PACKAGE;

@RequiredArgsConstructor(access = PACKAGE)
public class GeocodeSyncApiImpl implements GeocodeSyncAPI {

    private final GeocodeAsyncAPI delegate;

    @Override
    public SuggestionsResult citiesByState(CitiesByStateRequest request) {
        return await(delegate.citiesByState(request));
    }

    @Override
    public SuggestionsResult suggestions(SuggestionsBaseRequest request) {
        return await(delegate.suggestions(request));
    }

    @Override
    public SuggestionsResult structured(StructuredBaseRequest request) {
        return await(delegate.structured(request));
    }

    @Override
    public SuggestionsResult reverse(ReverseBaseRequest request) {
        return await(delegate.reverse(request));
    }

    @Override
    public SuggestionsResult crossCities(CrossCitiesRequest request) {
        return await(delegate.crossCities(request));
    }
}
