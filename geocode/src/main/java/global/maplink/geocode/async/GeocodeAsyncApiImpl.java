package global.maplink.geocode.async;

import global.maplink.MapLinkServiceRequestAsyncRunner;
import global.maplink.geocode.extensions.GeocodeExtensionManager;
import global.maplink.geocode.schema.GeocodeSplittableRequest;
import global.maplink.geocode.schema.crossCities.CrossCitiesRequest;
import global.maplink.geocode.schema.reverse.ReverseRequest;
import global.maplink.geocode.schema.structured.StructuredRequest;
import global.maplink.geocode.schema.suggestions.SuggestionsRequest;
import global.maplink.geocode.schema.suggestions.SuggestionsResult;
import global.maplink.helpers.FutureHelper;
import lombok.RequiredArgsConstructor;
import lombok.val;

import java.util.concurrent.CompletableFuture;

import static global.maplink.geocode.schema.suggestions.SuggestionsResult.EMPTY;
import static java.util.stream.Collectors.toList;
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
    public CompletableFuture<SuggestionsResult> reverse(ReverseRequest request) {
        return extensionManager.get(ReverseRequest.class).doRequest(request, this::runSplit);
    }

    @Override
    public CompletableFuture<SuggestionsResult> crossCities(CrossCitiesRequest request) {
        return extensionManager.get(CrossCitiesRequest.class).doRequest(request, runner::run);
    }

    private CompletableFuture<SuggestionsResult> runSplit(GeocodeSplittableRequest request) {
        val requests = request.split().stream().map(runner::run).collect(toList());
        return CompletableFuture.allOf(requests.toArray(new CompletableFuture[0]))
                .thenApply(v -> requests.stream()
                        .map(FutureHelper::await)
                        .reduce(SuggestionsResult::joinTo)
                        .orElse(EMPTY)
                );

    }

}
