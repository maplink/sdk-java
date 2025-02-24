package global.maplink.geocode.extensions;

import global.maplink.extensions.SdkExtension;
import global.maplink.geocode.schema.v1.GeocodeServiceRequest;
import global.maplink.geocode.schema.v1.suggestions.SuggestionsRequest;
import global.maplink.geocode.schema.v1.suggestions.SuggestionsResult;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.stream.Collectors;

import static java.util.Collections.emptyList;
import static java.util.concurrent.CompletableFuture.completedFuture;
import static java.util.stream.IntStream.range;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Answers.CALLS_REAL_METHODS;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class GeocodeExtensionManagerTest {

    @Test
    @SneakyThrows
    void mustWorkWithEmptyExtensions() {
        GeocodeExtensionManager mng = new GeocodeExtensionManager(emptyList());

        GeocodeExtension<SuggestionsRequest> extension = mng.get(SuggestionsRequest.class);

        assertThat(extension).isNotNull();
        assertThat(extension.doRequest(null, r -> completedFuture(SuggestionsResult.EMPTY)).get())
                .isEqualTo(SuggestionsResult.EMPTY);
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    @Test
    @SneakyThrows
    void mustWorkWithMultipleExtensions() {
        List<GeocodeExtension<GeocodeServiceRequest>> extensions = range(0, 10)
                .mapToObj(i -> {
                    GeocodeExtension ext = mock(GeocodeExtension.class, withSettings().defaultAnswer(CALLS_REAL_METHODS));
                    when(ext.getRequestType()).thenReturn(SuggestionsRequest.class);
                    when(ext.getName()).thenReturn("Extension nr " + i);
                    when(ext.getPriority()).thenReturn(SdkExtension.DEFAULT_PRIORITY + i);
                    return (GeocodeExtension<GeocodeServiceRequest>) ext;
                }).collect(Collectors.toList());

        GeocodeExtensionManager mng = new GeocodeExtensionManager(extensions);

        GeocodeExtension<SuggestionsRequest> extension = mng.get(SuggestionsRequest.class);

        assertThat(extension).isNotNull();
        assertThat(extension.doRequest(null, r -> completedFuture(SuggestionsResult.EMPTY)).get())
                .isEqualTo(SuggestionsResult.EMPTY);
        extensions.forEach(v -> verify(v, times(1)).doRequest(any(), any()));
    }
}
