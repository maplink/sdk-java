package global.maplink.geocode.async.v2;

import global.maplink.MapLinkServiceRequestAsyncRunner;
import global.maplink.geocode.async.GeocodeAsyncHelper;
import global.maplink.geocode.extensions.GeocodeExtensionManager;
import global.maplink.geocode.schema.v2.structured.StructuredRequest;
import global.maplink.geocode.schema.v2.suggestions.SuggestionsResult;
import global.maplink.geocode.schema.v2.reverse.ReverseBaseRequest;
import global.maplink.geocode.schema.v2.suggestions.SuggestionsBaseRequest;
import lombok.RequiredArgsConstructor;

import java.util.concurrent.CompletableFuture;

import static lombok.AccessLevel.PACKAGE;

@RequiredArgsConstructor(access = PACKAGE)
public class GeocodeAsyncApiImpl implements GeocodeAsyncAPI {

    private final MapLinkServiceRequestAsyncRunner runner;

    private final GeocodeExtensionManager extensionManager;

    @Override
    public CompletableFuture<SuggestionsResult> suggestions(SuggestionsBaseRequest request) {
        return extensionManager.get(SuggestionsBaseRequest.class).doRequest(request, runner::run);
    }

    @Override
    public CompletableFuture<SuggestionsResult> structured(StructuredRequest request) {
        return extensionManager.get(StructuredRequest.class).doRequest(request, runner::run);
    }


    @Override
    public CompletableFuture<SuggestionsResult> reverse(ReverseBaseRequest request) {
        return extensionManager.get(ReverseBaseRequest.class).doRequest(request, req -> GeocodeAsyncHelper.runSplit(runner, req));
    }


}
