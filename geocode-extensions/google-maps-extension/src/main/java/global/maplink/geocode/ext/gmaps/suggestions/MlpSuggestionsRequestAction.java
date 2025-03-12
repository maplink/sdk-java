package global.maplink.geocode.ext.gmaps.suggestions;

import global.maplink.geocode.schema.v1.suggestions.SuggestionsRequest;
import global.maplink.geocode.schema.v1.suggestions.SuggestionsResult;

import java.util.concurrent.CompletableFuture;
import java.util.function.Function;

@FunctionalInterface
public interface MlpSuggestionsRequestAction extends Function<SuggestionsRequest, CompletableFuture<SuggestionsResult>> {
}
