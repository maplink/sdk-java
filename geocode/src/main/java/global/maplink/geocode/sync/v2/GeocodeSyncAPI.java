package global.maplink.geocode.sync.v2;

import global.maplink.env.Environment;
import global.maplink.geocode.async.v2.GeocodeAsyncAPI;
import global.maplink.geocode.schema.Type;
import global.maplink.geocode.schema.reverse.ReverseRequest;
import global.maplink.geocode.schema.structured.StructuredRequest;
import global.maplink.geocode.schema.suggestions.SuggestionsRequest;
import global.maplink.geocode.schema.suggestions.SuggestionsResult;

import java.util.List;

import static java.util.Arrays.asList;

public interface GeocodeSyncAPI {


    default SuggestionsResult suggestions(String query) {
        return suggestions(SuggestionsRequest.builder().query(query).build());
    }

    default SuggestionsResult suggestions(String query, Type type) {
        return suggestions(SuggestionsRequest.builder().query(query).type(type).build());
    }

    SuggestionsResult suggestions(SuggestionsRequest request);

    SuggestionsResult structured(StructuredRequest request);

    default SuggestionsResult reverse(ReverseRequest.Entry... request) {
        return reverse(asList(request));
    }

    default SuggestionsResult reverse(List<ReverseRequest.Entry> request) {
        return reverse(ReverseRequest.of(request));
    }

    SuggestionsResult reverse(ReverseRequest request);

    static GeocodeSyncAPI getInstance() {
        return new GeocodeSyncApiImpl(GeocodeAsyncAPI.getInstance());
    }

    static GeocodeSyncAPI getInstance(Environment environment) {
        return new GeocodeSyncApiImpl(GeocodeAsyncAPI.getInstance(environment));
    }
}
