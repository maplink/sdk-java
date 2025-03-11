package global.maplink.geocode.sync.v2;

import global.maplink.geocode.async.v2.GeocodeAsyncAPI;
import global.maplink.geocode.schema.v2.structured.StructuredRequest;
import global.maplink.geocode.schema.v2.suggestions.SuggestionsResult;
import global.maplink.geocode.schema.v2.reverse.ReverseRequest;
import global.maplink.geocode.schema.v2.suggestions.SuggestionsBaseRequest;
import lombok.RequiredArgsConstructor;

import static global.maplink.helpers.FutureHelper.await;
import static lombok.AccessLevel.PACKAGE;

@RequiredArgsConstructor(access = PACKAGE)
public class GeocodeSyncApiImpl implements GeocodeSyncAPI {

    private final GeocodeAsyncAPI delegate;


    @Override
    public SuggestionsResult suggestions(SuggestionsBaseRequest request) {
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
