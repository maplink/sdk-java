package global.maplink.geocode.ext.gmaps.config;

import global.maplink.geocode.schema.suggestions.SuggestionsRequest;
import global.maplink.geocode.schema.suggestions.SuggestionsResult;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import static java.util.Optional.ofNullable;

public interface GeocodeGmapsSwitchStrategy {

    boolean shouldSwitchAfter(SuggestionsRequest request, SuggestionsResult result);

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
        public boolean shouldSwitchAfter(SuggestionsRequest request, SuggestionsResult result) {
            return ofNullable(result.getMostRelevant())
                    .map(r -> r.getScore() < threshold)
                    .orElse(true);
        }
    }
}
