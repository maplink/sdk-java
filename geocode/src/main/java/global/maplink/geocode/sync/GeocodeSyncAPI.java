package global.maplink.geocode.sync;

import global.maplink.env.Environment;
import global.maplink.geocode.async.GeocodeAsyncAPI;
import global.maplink.geocode.suggestions.SuggestionsRequest;
import global.maplink.geocode.suggestions.SuggestionsResponse;

public interface GeocodeSyncAPI {

    default SuggestionsResponse suggestions(String query) {
        return suggestions(SuggestionsRequest.builder().query(query).build());
    }

    SuggestionsResponse suggestions(SuggestionsRequest request);


    static GeocodeSyncAPI getInstance() {
        return new GeocodeSyncApiImpl(GeocodeAsyncAPI.getInstance());
    }

    static GeocodeSyncAPI getInstance(Environment environment) {
        return new GeocodeSyncApiImpl(GeocodeAsyncAPI.getInstance(environment));
    }
}
