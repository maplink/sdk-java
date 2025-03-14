package global.maplink.geocode.sync;

import global.maplink.env.Environment;
import global.maplink.geocode.GeocodeVersion;
import global.maplink.geocode.async.GeocodeAsyncAPI;
import global.maplink.geocode.schema.Type;
import global.maplink.geocode.schema.cities.CitiesByStateRequest;
import global.maplink.geocode.schema.crossCities.CrossCitiesRequest;
import global.maplink.geocode.schema.reverse.ReverseRequest;
import global.maplink.geocode.schema.structured.StructuredRequest;
import global.maplink.geocode.schema.suggestions.SuggestionsRequest;
import global.maplink.geocode.schema.suggestions.SuggestionsResult;

import java.util.List;

import static java.util.Arrays.asList;

public interface GeocodeSyncAPI {

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

    SuggestionsResult suggestions(SuggestionsRequest request);

    SuggestionsResult structured(StructuredRequest request);

    default SuggestionsResult reverse(ReverseRequest.Entry... request) {
        return reverse(asList(request));
    }

    default SuggestionsResult reverse(List<ReverseRequest.Entry> request) {
        return reverse(ReverseRequest.of(request));
    }

    SuggestionsResult reverse(ReverseRequest request);

    default SuggestionsResult crossCities(CrossCitiesRequest.Point... points) {
        return crossCities(asList(points));
    }

    default SuggestionsResult crossCities(List<CrossCitiesRequest.Point> points) {
        return crossCities(CrossCitiesRequest.of(points));
    }

    SuggestionsResult crossCities(CrossCitiesRequest request);

    static GeocodeSyncAPI getInstance(GeocodeVersion version) {
        return new GeocodeSyncApiImpl(GeocodeAsyncAPI.getInstance(version));
    }

    static GeocodeSyncAPI getInstance() {
        return new GeocodeSyncApiImpl(GeocodeAsyncAPI.getInstance());
    }

    static GeocodeSyncAPI getInstance(Environment environment) {
        return new GeocodeSyncApiImpl(GeocodeAsyncAPI.getInstance(environment));
    }

    static GeocodeSyncAPI getInstance(Environment environment, GeocodeVersion version) {
        return new GeocodeSyncApiImpl(GeocodeAsyncAPI.getInstance(environment, version));
    }
}
