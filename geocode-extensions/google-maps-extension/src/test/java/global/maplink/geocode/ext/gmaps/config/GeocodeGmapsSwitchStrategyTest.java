package global.maplink.geocode.ext.gmaps.config;

import global.maplink.geocode.ext.gmaps.config.GeocodeGmapsSwitchStrategy.SwitchOnLowScore;
import global.maplink.geocode.schema.suggestions.Suggestion;
import global.maplink.geocode.schema.suggestions.SuggestionsRequest;
import global.maplink.geocode.schema.suggestions.SuggestionsResult;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

class GeocodeGmapsSwitchStrategyTest {

    @Test
    void mustReturnDefaultStrategy() {
        assertThat(GeocodeGmapsSwitchStrategy.defaultStrategy()).isNotNull();
    }

    @Nested
    class SwitchOnLowScoreTest {

        @Test
        void shouldHaveDefaultValidThreshold() {
            SwitchOnLowScore strategy = new SwitchOnLowScore();
            assertThat(strategy.getThreshold()).isGreaterThan(0.0).isLessThanOrEqualTo(100.0);
        }


        @Test
        void shouldSwitchWhenTheResultIsEmpty() {
            SwitchOnLowScore strategy = new SwitchOnLowScore();
            SuggestionsRequest mockRequest = mock(SuggestionsRequest.class);

            assertThat(strategy.shouldSwitchAfter(mockRequest, SuggestionsResult.EMPTY)).isTrue();
        }

        @Test
        void shouldReturnWhenSwitchIsNeededBasedOnResultScore() {
            SwitchOnLowScore strategy = new SwitchOnLowScore(50.0);
            SuggestionsRequest mockRequest = mock(SuggestionsRequest.class);

            assertThat(strategy.shouldSwitchAfter(mockRequest, buildResultWithScore(90f))).isFalse();
            assertThat(strategy.shouldSwitchAfter(mockRequest, buildResultWithScore(30f))).isTrue();
        }

        private SuggestionsResult buildResultWithScore(float score) {
            return SuggestionsResult.builder()
                    .found(1)
                    .result(Suggestion.builder().score(score).build())
                    .build();
        }
    }

}