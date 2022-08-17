package global.maplink.geocode.sync;

import global.maplink.geocode.async.GeocodeAsyncAPI;
import global.maplink.geocode.schema.geocode.GeocodeRequest;
import global.maplink.geocode.schema.reverse.ReverseRequest;
import global.maplink.geocode.schema.suggestions.SuggestionsRequest;
import global.maplink.geocode.schema.suggestions.SuggestionsResult;
import lombok.RequiredArgsConstructor;

import static global.maplink.helpers.FutureHelper.await;
import static lombok.AccessLevel.PACKAGE;

@RequiredArgsConstructor(access = PACKAGE)
public class GeocodeSyncApiImpl implements GeocodeSyncAPI {

    private final GeocodeAsyncAPI delegate;

    @Override
    public SuggestionsResult suggestions(SuggestionsRequest request) {
        return await(delegate.suggestions(request));
    }

    @Override
    public SuggestionsResult geocode(GeocodeRequest request) {
        return await(delegate.geocode(request));
    }

    @Override
    public SuggestionsResult reverse(ReverseRequest request) {
        return await(delegate.reverse(request));
    }
}
