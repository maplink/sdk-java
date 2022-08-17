package global.maplink.geocode.sync;

import global.maplink.env.Environment;
import global.maplink.geocode.async.GeocodeAsyncAPI;
import global.maplink.geocode.geocode.GeocodeRequest;
import global.maplink.geocode.reverse.ReverseRequest;
import global.maplink.geocode.schema.Type;
import global.maplink.geocode.schema.suggestions.SuggestionsResult;
import global.maplink.geocode.suggestions.SuggestionsRequest;

import static java.util.Arrays.asList;

public interface GeocodeSyncAPI {

    default SuggestionsResult suggestions(String query) {
        return suggestions(SuggestionsRequest.builder().query(query).build());
    }

    default SuggestionsResult suggestions(String query, Type type) {
        return suggestions(SuggestionsRequest.builder().query(query).type(type).build());
    }

    SuggestionsResult suggestions(SuggestionsRequest request);

    SuggestionsResult geocode(GeocodeRequest request);

    default SuggestionsResult reverse(ReverseRequest.Entry... request){
        return reverse(ReverseRequest.builder().entries(asList(request)).build());
    }

    SuggestionsResult reverse(ReverseRequest request);

    static GeocodeSyncAPI getInstance() {
        return new GeocodeSyncApiImpl(GeocodeAsyncAPI.getInstance());
    }

    static GeocodeSyncAPI getInstance(Environment environment) {
        return new GeocodeSyncApiImpl(GeocodeAsyncAPI.getInstance(environment));
    }
}
