package global.maplink.geocode.ext.gmaps.suggestions;

import global.maplink.geocode.schema.v1.suggestions.SuggestionsRequest;

import java.util.concurrent.CompletableFuture;
import java.util.function.Function;

@FunctionalInterface
public interface GMapsSuggestionsRequestAction extends Function<SuggestionsRequest, CompletableFuture<GeocodeGMapsResponse>> {
}
