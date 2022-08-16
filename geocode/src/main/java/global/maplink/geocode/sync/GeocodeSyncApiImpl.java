package global.maplink.geocode.sync;

import global.maplink.geocode.async.GeocodeAsyncAPI;
import global.maplink.geocode.geocode.GeocodeRequest;
import global.maplink.geocode.suggestions.SuggestionsRequest;
import global.maplink.geocode.suggestions.SuggestionsResponse;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

import static global.maplink.helpers.FutureHelper.await;

@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
public class GeocodeSyncApiImpl implements GeocodeSyncAPI {

    private final GeocodeAsyncAPI delegate;

    @Override
    public SuggestionsResponse suggestions(SuggestionsRequest request) {
        return await(delegate.suggestions(request));
    }

    @Override
    public SuggestionsResponse geocode(GeocodeRequest request) {
        return await(delegate.geocode(request));
    }

}
