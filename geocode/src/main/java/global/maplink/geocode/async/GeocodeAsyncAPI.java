package global.maplink.geocode.async;

import global.maplink.MapLinkSDK;
import global.maplink.env.Environment;
import global.maplink.geocode.schema.Type;
import global.maplink.geocode.schema.crossCities.CrossCitiesRequest;
import global.maplink.geocode.schema.geocode.GeocodeRequest;
import global.maplink.geocode.schema.reverse.ReverseRequest;
import global.maplink.geocode.schema.suggestions.SuggestionsRequest;
import global.maplink.geocode.schema.suggestions.SuggestionsResult;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

import static java.util.Arrays.asList;

public interface GeocodeAsyncAPI {

    default CompletableFuture<SuggestionsResult> suggestions(String query) {
        return suggestions(SuggestionsRequest.builder().query(query).build());
    }

    default CompletableFuture<SuggestionsResult> suggestions(String query, Type type) {
        return suggestions(SuggestionsRequest.builder().query(query).type(type).build());
    }

    CompletableFuture<SuggestionsResult> suggestions(SuggestionsRequest request);

    CompletableFuture<SuggestionsResult> geocode(GeocodeRequest request);

    default CompletableFuture<SuggestionsResult> reverse(ReverseRequest.Entry... request) {
        return reverse(asList(request));
    }

    default CompletableFuture<SuggestionsResult> reverse(List<ReverseRequest.Entry> request) {
        return reverse(ReverseRequest.of(request));
    }

    CompletableFuture<SuggestionsResult> reverse(ReverseRequest request);

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
                Optional.ofNullable(environment).orElse(sdk.getEnvironment()),
                sdk.getHttp(),
                sdk.getJsonMapper(),
                sdk.getTokenProvider(),
                sdk.getCredentials()
        );
    }

}
