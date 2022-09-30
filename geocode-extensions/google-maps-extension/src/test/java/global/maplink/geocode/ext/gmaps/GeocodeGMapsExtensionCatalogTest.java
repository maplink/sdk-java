package global.maplink.geocode.ext.gmaps;

import global.maplink.geocode.ext.gmaps.config.GeocodeGMapsConfig;
import global.maplink.geocode.ext.gmaps.suggestions.GeocodeSuggestionsGMapsExtension;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class GeocodeGMapsExtensionCatalogTest {

    @Test
    void mustPropagateConfigToInternalExtensions() {
        GeocodeGMapsConfig config = new GeocodeGMapsConfig("TESTE");
        GeocodeGMapsExtensionCatalog catalog = new GeocodeGMapsExtensionCatalog(config);
        GeocodeSuggestionsGMapsExtension first = (GeocodeSuggestionsGMapsExtension) catalog.getAll().stream()
                .filter(e -> e instanceof GeocodeSuggestionsGMapsExtension)
                .findFirst().orElseThrow(IllegalArgumentException::new);

        assertThat(first.getConfig()).isSameAs(config);
    }

}