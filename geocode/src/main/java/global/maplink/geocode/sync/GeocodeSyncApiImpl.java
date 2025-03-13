package global.maplink.geocode.sync;

import global.maplink.geocode.async.GeocodeAsyncAPI;
import global.maplink.geocode.schema.cities.CitiesByStateRequest;
import global.maplink.geocode.schema.crossCities.CrossCitiesRequest;
import global.maplink.geocode.schema.reverse.ReverseRequest;
import global.maplink.geocode.schema.structured.StructuredRequest;
import global.maplink.geocode.schema.suggestions.SuggestionsRequest;
import global.maplink.geocode.schema.suggestions.SuggestionsResult;
import lombok.RequiredArgsConstructor;

import static global.maplink.helpers.FutureHelper.await;
import static lombok.AccessLevel.PUBLIC;

@RequiredArgsConstructor(access = PUBLIC)
public class GeocodeSyncApiImpl implements GeocodeSyncAPI {

    private final GeocodeAsyncAPI delegate;

    @Override
    public SuggestionsResult citiesByState(CitiesByStateRequest request) {
        return await(delegate.citiesByState(request));
    }

    @Override
    public SuggestionsResult suggestions(SuggestionsRequest request) {
        return await(delegate.suggestions(request));
    }

    @Override
    public SuggestionsResult structured(StructuredRequest request) {
        return await(delegate.structured(request));
    }

    @Override
    public SuggestionsResult reverse(ReverseRequest request) {
        return await(delegate.reverse(request));
    }

    @Override
    public SuggestionsResult crossCities(CrossCitiesRequest request) {
        return await(delegate.crossCities(request));
    }
}
