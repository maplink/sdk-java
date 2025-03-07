package global.maplink.geocode.sync.v1;

import global.maplink.env.Environment;
import global.maplink.geocode.async.v1.GeocodeAsyncAPI;
import global.maplink.geocode.schema.v1.cities.CitiesByStateRequest;
import global.maplink.geocode.schema.v1.crossCities.CrossCitiesRequest;
import global.maplink.geocode.schema.v1.reverse.ReverseRequest;
import global.maplink.geocode.schema.v1.suggestions.SuggestionsRequest;
import global.maplink.geocode.schema.v2.suggestions.SuggestionsResult;
import global.maplink.geocode.schema.v2.Type;
import global.maplink.geocode.schema.v2.reverse.ReverseBaseRequest;
import global.maplink.geocode.sync.GeocodeSyncAPIBase;

import java.util.List;

import static java.util.Arrays.asList;

public interface GeocodeSyncAPI extends GeocodeSyncAPIBase {

    SuggestionsResult citiesByState(CitiesByStateRequest request);

    default SuggestionsResult citiesByState(String state) {
        return citiesByState(CitiesByStateRequest.builder().state(state).build());
    }

    default SuggestionsResult suggestions(String query) {
        return suggestions(SuggestionsRequest.builder().query(query).build());
    }

    default SuggestionsResult suggestions(String query, Type type) {
        return suggestions(SuggestionsRequest.builder().query(query).type(type).build());
    }

    default SuggestionsResult reverse(ReverseBaseRequest.Entry... request) {
        return reverse(asList(request));
    }

    default SuggestionsResult reverse(List<ReverseBaseRequest.Entry> request) {
        return reverse(ReverseRequest.of(request));
    }

    default SuggestionsResult crossCities(CrossCitiesRequest.Point... points) {
        return crossCities(asList(points));
    }

    default SuggestionsResult crossCities(List<CrossCitiesRequest.Point> points) {
        return crossCities(CrossCitiesRequest.of(points));
    }

    SuggestionsResult crossCities(CrossCitiesRequest request);

    static GeocodeSyncAPI getInstance() {
        return new GeocodeSyncApiImpl(GeocodeAsyncAPI.getInstance());
    }

    static GeocodeSyncAPI getInstance(Environment environment) {
        return new GeocodeSyncApiImpl(GeocodeAsyncAPI.getInstance(environment));
    }
}
