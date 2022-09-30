package global.maplink.geocode.ext.gmaps.suggestions;

import global.maplink.MapLinkSDK;
import global.maplink.credentials.MapLinkCredentials;
import global.maplink.env.EnvironmentCatalog;
import global.maplink.geocode.ext.gmaps.GeocodeGMapsConfig;
import global.maplink.geocode.schema.suggestions.Suggestion;
import global.maplink.geocode.schema.suggestions.SuggestionsRequest;
import global.maplink.geocode.schema.suggestions.SuggestionsResult;
import lombok.SneakyThrows;
import org.assertj.core.api.ThrowingConsumer;
import org.junit.jupiter.api.Test;

import java.util.concurrent.ExecutionException;

import static global.maplink.geocode.ext.gmaps.suggestions.GeocodeGMapsResponse.STATUS_DENIED;
import static java.util.concurrent.CompletableFuture.completedFuture;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class GeocodeSuggestionsGMapsExtensionTest {

    private final GeocodeGMapsConfig mockConfig = new GeocodeGMapsConfig("Teste");

    private final SuggestionsRequest mockRequest = SuggestionsRequest.builder().query("Test").build();

    @Test
    @SneakyThrows
    void mustReturnSameResponseWhenNotInitialized() {
        SuggestionsResult expected = SuggestionsResult.builder().build();
        GeocodeSuggestionsGMapsExtension extension = new GeocodeSuggestionsGMapsExtension(mockConfig);
        SuggestionsResult result = extension.doRequest(mockRequest, request -> completedFuture(expected)).get();
        assertThat(result).isSameAs(expected);
    }

    @Test
    void mustReturnSameResponseWhenScoreIsGoodEnough() {
        withMockSdk(sdk -> {
            SuggestionsResult expected = SuggestionsResult.builder().found(10).result(Suggestion.builder().score(99f).build()).build();
            GeocodeSuggestionsGMapsExtension extension = new GeocodeSuggestionsGMapsExtension(mockConfig);
            extension.initialize(sdk);
            SuggestionsResult result = extension.doRequest(mockRequest, request -> completedFuture(expected)).get();
            assertThat(result).isSameAs(expected);
        });
    }

    @Test
    void mustReturnCallGoogleMapsWhenScoreIsTooLowAndFailWithInvalidCredential() {
        withMockSdk(sdk -> {
            SuggestionsResult expected = SuggestionsResult.builder().found(10).result(Suggestion.builder().score(0f).build()).build();
            GeocodeSuggestionsGMapsExtension extension = new GeocodeSuggestionsGMapsExtension(mockConfig);
            extension.initialize(sdk);
            assertThatThrownBy(() -> extension.doRequest(mockRequest, request -> completedFuture(expected)).get())
                    .isInstanceOf(ExecutionException.class)
                    .cause()
                    .isInstanceOf(IllegalArgumentException.class)
                    .message().contains(STATUS_DENIED);
        });
    }

    private void withMockSdk(ThrowingConsumer<MapLinkSDK> action) {
        try {
            MapLinkSDK.configure()
                    .with(MapLinkCredentials.ofKey("teste", "teste"))
                    .with(EnvironmentCatalog.HOMOLOG)
                    .initialize();
            action.accept(MapLinkSDK.getInstance());
        } finally {
            MapLinkSDK.resetConfiguration();
        }
    }
}