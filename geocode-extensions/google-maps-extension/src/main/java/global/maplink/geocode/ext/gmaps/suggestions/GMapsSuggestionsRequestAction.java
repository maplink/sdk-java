package global.maplink.geocode.ext.gmaps.suggestions;

import global.maplink.geocode.schema.v2.suggestions.SuggestionsBaseRequest;

import java.util.concurrent.CompletableFuture;
import java.util.function.Function;

@FunctionalInterface
public interface GMapsSuggestionsRequestAction extends Function<SuggestionsBaseRequest, CompletableFuture<GeocodeGMapsResponse>> {
}
