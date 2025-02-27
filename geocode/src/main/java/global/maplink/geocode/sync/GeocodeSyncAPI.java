package global.maplink.geocode.sync;

import global.maplink.env.Environment;
import global.maplink.geocode.async.GeocodeAsyncAPI;
import global.maplink.geocode.schema.Type;
import global.maplink.geocode.schema.v1.cities.CitiesByStateRequest;
import global.maplink.geocode.schema.v1.crossCities.CrossCitiesRequest;
import global.maplink.geocode.schema.v2.reverse.ReverseBaseRequest;
import global.maplink.geocode.schema.v2.structured.StructuredBaseRequest;
import global.maplink.geocode.schema.v2.suggestions.SuggestionsBaseRequest;
import global.maplink.geocode.schema.v1.suggestions.SuggestionsResult;

import java.util.List;

import static java.util.Arrays.asList;

public interface GeocodeSyncAPI {

    SuggestionsResult citiesByState(CitiesByStateRequest request);

    default SuggestionsResult citiesByState(String state) {
        return citiesByState(CitiesByStateRequest.builder().state(state).build());
    }

    default SuggestionsResult suggestions(String query) {
        return suggestions(SuggestionsBaseRequest.builder().query(query).build());
    }

    default SuggestionsResult suggestions(String query, Type type) {
        return suggestions(SuggestionsBaseRequest.builder().query(query).type(type).build());
    }

    SuggestionsResult suggestions(SuggestionsBaseRequest request);

    SuggestionsResult structured(StructuredBaseRequest request);

    default SuggestionsResult reverse(ReverseBaseRequest.Entry... request) {
        return reverse(asList(request));
    }

    default SuggestionsResult reverse(List<ReverseBaseRequest.Entry> request) {
        return reverse(ReverseBaseRequest.of(request));
    }

    SuggestionsResult reverse(ReverseBaseRequest request);

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
