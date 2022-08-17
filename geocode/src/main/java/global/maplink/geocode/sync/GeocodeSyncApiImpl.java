package global.maplink.geocode.sync;

import global.maplink.geocode.async.GeocodeAsyncAPI;
import global.maplink.geocode.geocode.GeocodeRequest;
import global.maplink.geocode.reverse.ReverseRequest;
import global.maplink.geocode.suggestions.SuggestionsRequest;
import global.maplink.geocode.suggestions.SuggestionsResponse;
import lombok.RequiredArgsConstructor;

import static global.maplink.helpers.FutureHelper.await;
import static lombok.AccessLevel.PACKAGE;

@RequiredArgsConstructor(access = PACKAGE)
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

    @Override
    public SuggestionsResponse reverse(ReverseRequest request) {
        return await(delegate.reverse(request));
    }
}
