package global.maplink.geocode.sync;

import global.maplink.env.Environment;
import global.maplink.geocode.async.GeocodeAsyncAPI;
import global.maplink.geocode.common.Type;
import global.maplink.geocode.geocode.GeocodeRequest;
import global.maplink.geocode.reverse.ReverseRequest;
import global.maplink.geocode.suggestions.SuggestionsRequest;
import global.maplink.geocode.suggestions.SuggestionsResponse;

import static java.util.Arrays.asList;

public interface GeocodeSyncAPI {

    default SuggestionsResponse suggestions(String query) {
        return suggestions(SuggestionsRequest.builder().query(query).build());
    }

    default SuggestionsResponse suggestions(String query, Type type) {
        return suggestions(SuggestionsRequest.builder().query(query).type(type).build());
    }

    SuggestionsResponse suggestions(SuggestionsRequest request);

    SuggestionsResponse geocode(GeocodeRequest request);

    default SuggestionsResponse reverse(ReverseRequest.Entry... request){
        return reverse(ReverseRequest.builder().entries(asList(request)).build());
    }

    SuggestionsResponse reverse(ReverseRequest request);

    static GeocodeSyncAPI getInstance() {
        return new GeocodeSyncApiImpl(GeocodeAsyncAPI.getInstance());
    }

    static GeocodeSyncAPI getInstance(Environment environment) {
        return new GeocodeSyncApiImpl(GeocodeAsyncAPI.getInstance(environment));
    }
}
