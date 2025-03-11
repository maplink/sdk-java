package global.maplink.geocode.ext.gmaps.suggestions;

import global.maplink.geocode.schema.v2.suggestions.SuggestionsBaseRequest;
import global.maplink.geocode.schema.v1.suggestions.SuggestionsResult;

import java.util.concurrent.CompletableFuture;
import java.util.function.Function;

@FunctionalInterface
public interface MlpSuggestionsRequestAction extends Function<SuggestionsBaseRequest, CompletableFuture<SuggestionsResult>> {
}
