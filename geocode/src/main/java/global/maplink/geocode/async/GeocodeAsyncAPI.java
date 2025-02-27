package global.maplink.geocode.async;

import global.maplink.MapLinkSDK;
import global.maplink.env.Environment;
import global.maplink.geocode.extensions.GeocodeExtensionManager;
import global.maplink.geocode.schema.Type;
import global.maplink.geocode.schema.v1.cities.CitiesByStateRequest;
import global.maplink.geocode.schema.v1.crossCities.CrossCitiesRequest;
import global.maplink.geocode.schema.v1.reverse.ReverseRequest;
import global.maplink.geocode.schema.v2.reverse.ReverseBaseRequest;
import global.maplink.geocode.schema.v2.structured.StructuredBaseRequest;
import global.maplink.geocode.schema.v2.suggestions.SuggestionsBaseRequest;
import global.maplink.geocode.schema.v1.suggestions.SuggestionsResult;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

import static global.maplink.MapLinkServiceRequestAsyncRunner.createRunner;
import static java.util.Arrays.asList;

public interface GeocodeAsyncAPI {

    default CompletableFuture<SuggestionsResult> suggestions(String query) {
        return suggestions(SuggestionsBaseRequest.builder().query(query).build());
    }

    default CompletableFuture<SuggestionsResult> suggestions(String query, Type type) {
        return suggestions(SuggestionsBaseRequest.builder().query(query).type(type).build());
    }

    CompletableFuture<SuggestionsResult> suggestions(SuggestionsBaseRequest request);

    default CompletableFuture<SuggestionsResult> citiesByState(String state) {
        return citiesByState(CitiesByStateRequest.builder().state(state).build());
    }

    CompletableFuture<SuggestionsResult> citiesByState(CitiesByStateRequest request);

    CompletableFuture<SuggestionsResult> structured(StructuredBaseRequest request);

    default CompletableFuture<SuggestionsResult> reverse(ReverseBaseRequest.Entry... request) {
        return reverse(asList(request));
    }

    default CompletableFuture<SuggestionsResult> reverse(List<ReverseBaseRequest.Entry> request) {
        return reverse(ReverseRequest.of(request));
    }

    CompletableFuture<SuggestionsResult> reverse(ReverseBaseRequest request);

    default CompletableFuture<SuggestionsResult> crossCities(CrossCitiesRequest.Point... points) {
        return crossCities(asList(points));
    }

    default CompletableFuture<SuggestionsResult> crossCities(List<CrossCitiesRequest.Point> points) {
        return crossCities(CrossCitiesRequest.of(points));
    }

    CompletableFuture<SuggestionsResult> crossCities(CrossCitiesRequest request);

    static GeocodeAsyncAPI getInstance() {
        return getInstance(null);
    }

    static GeocodeAsyncAPI getInstance(Environment environment) {
        MapLinkSDK sdk = MapLinkSDK.getInstance();
        return new GeocodeAsyncApiImpl(
                createRunner(
                        Optional.ofNullable(environment).orElse(sdk.getEnvironment()),
                        sdk
                ),
                GeocodeExtensionManager.from(sdk.getExtensions())
        );
    }

}
