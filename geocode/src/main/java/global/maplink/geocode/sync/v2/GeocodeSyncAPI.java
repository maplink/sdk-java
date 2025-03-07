package global.maplink.geocode.sync.v2;

import global.maplink.env.Environment;
import global.maplink.geocode.async.v2.GeocodeAsyncAPI;
import global.maplink.geocode.schema.v2.suggestions.SuggestionsResult;
import global.maplink.geocode.schema.v2.Type;
import global.maplink.geocode.schema.v2.reverse.ReverseBaseRequest;
import global.maplink.geocode.schema.v2.suggestions.SuggestionsBaseRequest;
import global.maplink.geocode.sync.GeocodeSyncAPIBase;

import java.util.List;

import static java.util.Arrays.asList;

public interface GeocodeSyncAPI extends GeocodeSyncAPIBase {


    default SuggestionsResult suggestions(String query) {
        return suggestions(SuggestionsBaseRequest.builder().query(query).build());
    }

    default SuggestionsResult suggestions(String query, Type type) {
        return suggestions(SuggestionsBaseRequest.builder().query(query).type(type).build());
    }

    default SuggestionsResult reverse(ReverseBaseRequest.Entry... request) {
        return reverse(asList(request));
    }

    default SuggestionsResult reverse(List<ReverseBaseRequest.Entry> request) {
        return reverse(ReverseBaseRequest.of(request));
    }

    static GeocodeSyncAPI getInstance() {
        return new GeocodeSyncApiImpl(GeocodeAsyncAPI.getInstance());
    }

    static GeocodeSyncAPI getInstance(Environment environment) {
        return new GeocodeSyncApiImpl(GeocodeAsyncAPI.getInstance(environment));
    }
}
