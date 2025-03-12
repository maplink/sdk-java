package global.maplink.geocode.async;

import global.maplink.MapLinkServiceRequestAsyncRunner;
import global.maplink.geocode.schema.GeocodeSplittableRequest;
import global.maplink.geocode.schema.suggestions.SuggestionsResult;
import global.maplink.helpers.FutureHelper;
import lombok.RequiredArgsConstructor;
import lombok.val;

import java.util.concurrent.CompletableFuture;

import static global.maplink.geocode.schema.suggestions.SuggestionsResult.EMPTY;
import static java.util.stream.Collectors.toList;
import static lombok.AccessLevel.PACKAGE;

@RequiredArgsConstructor(access = PACKAGE)
public final class GeocodeAsyncHelper {

    public static CompletableFuture<SuggestionsResult> runSplit(MapLinkServiceRequestAsyncRunner runner, GeocodeSplittableRequest request) {
        val requests = request.split().stream().map(runner::run).collect(toList());
        return CompletableFuture.allOf(requests.toArray(new CompletableFuture[0]))
                .thenApply(v -> requests.stream()
                        .map(FutureHelper::await)
                        .reduce(SuggestionsResult::joinTo)
                        .orElse(EMPTY)
                );

    }
}
