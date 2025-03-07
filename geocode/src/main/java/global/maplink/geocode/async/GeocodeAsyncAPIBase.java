package global.maplink.geocode.async;

import global.maplink.geocode.schema.v2.structured.StructuredRequest;
import global.maplink.geocode.schema.v2.suggestions.SuggestionsResult;
import global.maplink.geocode.schema.v2.reverse.ReverseBaseRequest;
import global.maplink.geocode.schema.v2.suggestions.SuggestionsBaseRequest;

import java.util.concurrent.CompletableFuture;

public interface GeocodeAsyncAPIBase {

    CompletableFuture<SuggestionsResult> suggestions(SuggestionsBaseRequest request);

    CompletableFuture<SuggestionsResult> reverse(ReverseBaseRequest request);

    CompletableFuture<SuggestionsResult> structured(StructuredRequest request);

}
