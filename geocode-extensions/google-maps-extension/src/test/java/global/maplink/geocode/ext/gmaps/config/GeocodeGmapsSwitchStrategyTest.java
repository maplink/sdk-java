package global.maplink.geocode.ext.gmaps.config;

import global.maplink.geocode.ext.gmaps.config.GeocodeGmapsSwitchStrategy.GMapsFirstSwitchOnEmptyStrategy;
import global.maplink.geocode.ext.gmaps.config.GeocodeGmapsSwitchStrategy.SwitchOnLowScore;
import global.maplink.geocode.ext.gmaps.suggestions.*;
import global.maplink.geocode.schema.suggestions.Suggestion;
import global.maplink.geocode.schema.suggestions.SuggestionsRequest;
import global.maplink.geocode.schema.suggestions.SuggestionsResult;
import lombok.val;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.HashSet;

import static global.maplink.geocode.ext.gmaps.suggestions.GeocodeGMapsResponse.STATUS_EMPTY;
import static global.maplink.geocode.ext.gmaps.suggestions.GeocodeGMapsResponse.STATUS_OK;
import static java.util.Collections.*;
import static java.util.concurrent.CompletableFuture.completedFuture;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class GeocodeGmapsSwitchStrategyTest {

    @Test
    void mustReturnDefaultStrategy() {
        assertThat(GeocodeGmapsSwitchStrategy.defaultStrategy()).isNotNull();
    }

    @Nested
    class SwitchOnLowScoreTest {

        @Test
        void shouldHaveDefaultValidThreshold() {
            val strategy = new SwitchOnLowScore();
            assertThat(strategy.getThreshold()).isGreaterThan(0.0).isLessThanOrEqualTo(100.0);
        }

        @Test
        void shouldSwitchWhenTheResultIsEmpty() {
            val strategy = new SwitchOnLowScore();
            val request = SuggestionsRequest.builder().query("Teste").build();

            val gMapsResponse = gMapsResponse();
            val gmapsAction = mock(GMapsSuggestionsRequestAction.class);
            when(gmapsAction.apply(request)).thenReturn(completedFuture(gMapsResponse));

            val mlpAction = mock(MlpSuggestionsRequestAction.class);
            when(mlpAction.apply(request)).thenReturn(completedFuture(SuggestionsResult.EMPTY));

            assertThat(strategy.choose(request, gmapsAction, mlpAction))
                    .isCompletedWithValue(gMapsResponse.toSuggestions());

            verify(mlpAction, times(1)).apply(request);
            verify(gmapsAction, times(1)).apply(request);
        }

        @Test
        void shouldReturnWhenSwitchIsNeededBasedOnResultScore() {
            val strategy = new SwitchOnLowScore(50.0);
            val request = SuggestionsRequest.builder().query("Teste").build();

            val gMapsResponse = gMapsResponse();
            val gmapsAction = mock(GMapsSuggestionsRequestAction.class);
            when(gmapsAction.apply(request)).thenReturn(completedFuture(gMapsResponse));

            val highScoreMlpResult = buildResultWithScore(90f);
            val mlpHighScoreAction = mock(MlpSuggestionsRequestAction.class);
            when(mlpHighScoreAction.apply(request)).thenReturn(completedFuture(highScoreMlpResult));

            val lowScoreMlpResult = buildResultWithScore(30f);
            val mlpLowScoreAction = mock(MlpSuggestionsRequestAction.class);
            when(mlpLowScoreAction.apply(request)).thenReturn(completedFuture(lowScoreMlpResult));

            assertThat(strategy.choose(request, gmapsAction, mlpHighScoreAction))
                    .isCompletedWithValue(highScoreMlpResult);

            verify(mlpHighScoreAction, times(1)).apply(request);
            verify(gmapsAction, times(0)).apply(request);

            assertThat(strategy.choose(request, gmapsAction, mlpLowScoreAction))
                    .isCompletedWithValue(gMapsResponse.toSuggestions());

            verify(mlpLowScoreAction, times(1)).apply(request);
            verify(gmapsAction, times(1)).apply(request);
        }

    }

    @Nested
    class GMapsFirstStrategyTest {

        @Test
        void shouldSwitchToMaplinkWhenGoogleReturnsEmpty() {
            val strategy = new GMapsFirstSwitchOnEmptyStrategy();
            val request = SuggestionsRequest.builder().query("Teste").build();

            val gMapsResponse = emptyGmapsResponse();
            val gmapsAction = mock(GMapsSuggestionsRequestAction.class);
            when(gmapsAction.apply(request)).thenReturn(completedFuture(gMapsResponse));

            val mlpResult = buildResultWithScore(70);
            val mlpAction = mock(MlpSuggestionsRequestAction.class);
            when(mlpAction.apply(request)).thenReturn(completedFuture(mlpResult));

            assertThat(strategy.choose(request, gmapsAction, mlpAction)).isCompletedWithValue(mlpResult);

            verify(gmapsAction, times(1)).apply(request);
            verify(mlpAction, times(1)).apply(request);
        }

        @Test
        void shouldHitOnlyGmapsWhenGMapsResultIsNotEmpty() {
            val strategy = new GMapsFirstSwitchOnEmptyStrategy();
            val request = SuggestionsRequest.builder().query("Teste").build();

            val gMapsResponse = gMapsResponse();
            val gmapsAction = mock(GMapsSuggestionsRequestAction.class);
            when(gmapsAction.apply(request)).thenReturn(completedFuture(gMapsResponse));

            val mlpResult = buildResultWithScore(70);
            val mlpAction = mock(MlpSuggestionsRequestAction.class);
            when(mlpAction.apply(request)).thenReturn(completedFuture(mlpResult));

            assertThat(strategy.choose(request, gmapsAction, mlpAction))
                    .isCompletedWithValue(gMapsResponse.toSuggestions());

            verify(gmapsAction, times(1)).apply(request);
            verify(mlpAction, times(0)).apply(request);
        }
    }

    private GeocodeGMapsResponse emptyGmapsResponse() {
        return new GeocodeGMapsResponse(emptyList(), STATUS_EMPTY, null);
    }

    private GeocodeGMapsResponse gMapsResponse() {
        return new GeocodeGMapsResponse(singletonList(
                new GeocodeGMapsResult(
                        singletonList(
                                new GMapsAddressComponent(
                                        "sao paulo",
                                        "SP",
                                        new HashSet<>(singletonList(
                                                "administrative_area_level_1"
                                        ))
                                )
                        ),
                        "são paulo, SP",
                        null,
                        null,
                        emptySet(),
                        false
                )
        ), STATUS_OK, null);
    }

    private SuggestionsResult buildResultWithScore(float score) {
        return SuggestionsResult.builder()
                .found(1)
                .result(Suggestion.builder().score(score).build())
                .build();
    }

}