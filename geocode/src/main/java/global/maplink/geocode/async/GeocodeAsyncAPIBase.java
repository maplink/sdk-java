package global.maplink.geocode.async;

import global.maplink.geocode.schema.v2.structured.StructuredRequest;
import global.maplink.geocode.schema.v2.suggestions.SuggestionsBaseRequest;
import global.maplink.geocode.schema.v1.suggestions.SuggestionsResult;

import java.util.concurrent.CompletableFuture;

public interface GeocodeAsyncAPIBase {

    CompletableFuture<SuggestionsResult> suggestions(SuggestionsBaseRequest request);

    CompletableFuture<SuggestionsResult> structured(StructuredRequest request);



}
