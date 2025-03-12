package global.maplink.geocode.async.v1;

import global.maplink.MapLinkServiceRequestAsyncRunner;
import global.maplink.geocode.async.GeocodeAsyncHelper;
import global.maplink.geocode.extensions.GeocodeExtensionManager;
import global.maplink.geocode.schema.cities.CitiesByStateRequest;
import global.maplink.geocode.schema.crossCities.CrossCitiesRequest;
import global.maplink.geocode.schema.reverse.ReverseRequest;
import global.maplink.geocode.schema.structured.StructuredRequest;
import global.maplink.geocode.schema.suggestions.SuggestionsRequest;
import global.maplink.geocode.schema.suggestions.SuggestionsResult;
import lombok.RequiredArgsConstructor;

import java.util.concurrent.CompletableFuture;

import static lombok.AccessLevel.PACKAGE;

@RequiredArgsConstructor(access = PACKAGE)
public class GeocodeAsyncApiImpl implements GeocodeAsyncAPI {

    private final MapLinkServiceRequestAsyncRunner runner;

    private final GeocodeExtensionManager extensionManager;

    @Override
    public CompletableFuture<SuggestionsResult> suggestions(SuggestionsRequest request) {
        return extensionManager.get(SuggestionsRequest.class).doRequest(request, runner::run);
    }

    @Override
    public CompletableFuture<SuggestionsResult> structured(StructuredRequest request) {
        return extensionManager.get(StructuredRequest.class).doRequest(request, runner::run);
    }

    @Override
    public CompletableFuture<SuggestionsResult> citiesByState(CitiesByStateRequest request) {
        return extensionManager.get(CitiesByStateRequest.class).doRequest(request, runner::run);
    }

    @Override
    public CompletableFuture<SuggestionsResult> reverse(ReverseRequest request) {
        return extensionManager.get(ReverseRequest.class).doRequest(request, req -> GeocodeAsyncHelper.runSplit(runner, req));
    }

    @Override
    public CompletableFuture<SuggestionsResult> crossCities(CrossCitiesRequest request) {
        return extensionManager.get(CrossCitiesRequest.class).doRequest(request, runner::run);
    }

}
