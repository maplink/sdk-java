package global.maplink.geocode.sync;

import global.maplink.geocode.async.GeocodeAsyncAPI;
import global.maplink.geocode.suggestions.SuggestionsRequest;
import global.maplink.geocode.suggestions.SuggestionsResponse;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

import java.util.concurrent.ExecutionException;

@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
public class GeocodeSyncApiImpl implements GeocodeSyncAPI {

    private final GeocodeAsyncAPI delegate;

    @Override
    public SuggestionsResponse suggestions(SuggestionsRequest request) {
        try {
            return delegate.suggestions(request).get();
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }


}
