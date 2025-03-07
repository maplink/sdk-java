package global.maplink.geocode.ext.gmaps.config;

import global.maplink.geocode.ext.gmaps.suggestions.GMapsSuggestionsRequestAction;
import global.maplink.geocode.ext.gmaps.suggestions.GeocodeGMapsResponse;
import global.maplink.geocode.ext.gmaps.suggestions.MlpSuggestionsRequestAction;
import global.maplink.geocode.schema.v2.suggestions.Suggestion;
import global.maplink.geocode.schema.v2.suggestions.SuggestionsBaseRequest;
import global.maplink.geocode.schema.v2.suggestions.SuggestionsResult;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.concurrent.CompletableFuture;

import static java.util.Optional.ofNullable;
import static java.util.concurrent.CompletableFuture.completedFuture;

public interface GeocodeGmapsSwitchStrategy {

    CompletableFuture<SuggestionsResult> choose(
            SuggestionsBaseRequest request,
            GMapsSuggestionsRequestAction gmapsAction,
            MlpSuggestionsRequestAction mlpAction
    );

    static GeocodeGmapsSwitchStrategy defaultStrategy() {
        return new SwitchOnLowScore();
    }

    @RequiredArgsConstructor
    @Getter
    class SwitchOnLowScore implements GeocodeGmapsSwitchStrategy {

        public static final double DEFAULT_THRESHOLD = 70.0;

        private final double threshold;

        public SwitchOnLowScore() {
            this(DEFAULT_THRESHOLD);
        }

        @Override
        public CompletableFuture<SuggestionsResult> choose(
                SuggestionsBaseRequest request,
                GMapsSuggestionsRequestAction gmapsAction,
                MlpSuggestionsRequestAction mlpAction
        ) {
            return mlpAction.apply(request).thenCompose(result -> {
                if (shouldSwitchAfter(result))
                    return gmapsAction.apply(request).thenApply(GeocodeGMapsResponse::toSuggestions);
                else return completedFuture(result);
            });
        }

        private boolean shouldSwitchAfter(SuggestionsResult result) {
            return ofNullable(result.getMostRelevant())
                    .map(Suggestion::getScore)
                    .map(score -> score < threshold)
                    .orElse(true);
        }
    }

    @Getter
    class GMapsFirstSwitchOnEmptyStrategy implements GeocodeGmapsSwitchStrategy {

        @Override
        public CompletableFuture<SuggestionsResult> choose(
                SuggestionsBaseRequest request,
                GMapsSuggestionsRequestAction gmapsAction,
                MlpSuggestionsRequestAction mlpAction
        ) {
            return gmapsAction.apply(request).thenCompose(r -> {
                if (r.isEmpty()) return mlpAction.apply(request);
                else return completedFuture(r.toSuggestions());
            });
        }
    }
}
