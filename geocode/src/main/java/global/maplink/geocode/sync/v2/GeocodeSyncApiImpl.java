package global.maplink.geocode.sync.v2;

import global.maplink.geocode.async.v2.GeocodeAsyncAPI;
import global.maplink.geocode.schema.v1.suggestions.SuggestionsRequest;
import global.maplink.geocode.schema.v2.structured.StructuredRequest;
import global.maplink.geocode.schema.v1.suggestions.SuggestionsResult;
import global.maplink.geocode.schema.v1.reverse.ReverseRequest;
import lombok.RequiredArgsConstructor;

import static global.maplink.helpers.FutureHelper.await;
import static lombok.AccessLevel.PACKAGE;

@RequiredArgsConstructor(access = PACKAGE)
public class GeocodeSyncApiImpl implements GeocodeSyncAPI {

    private final GeocodeAsyncAPI delegate;

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

}
