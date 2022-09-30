package global.maplink.geocode.ext.gmaps;

import global.maplink.extensions.SdkExtension;
import global.maplink.extensions.SdkExtensionCatalog;
import global.maplink.geocode.ext.gmaps.config.GeocodeGMapsConfig;
import global.maplink.geocode.ext.gmaps.suggestions.GeocodeSuggestionsGMapsExtension;
import global.maplink.geocode.extensions.GeocodeExtension;
import global.maplink.geocode.schema.GeocodeServiceRequest;

import java.util.Collection;
import java.util.List;

import static java.util.Collections.singletonList;
import static java.util.stream.Collectors.toSet;

public class GeocodeGMapsExtensionCatalog implements SdkExtensionCatalog {

    private final List<GeocodeExtension<? extends GeocodeServiceRequest>> all;

    public GeocodeGMapsExtensionCatalog() {
        this(GeocodeGMapsConfig.fromEnv());
    }

    public GeocodeGMapsExtensionCatalog(GeocodeGMapsConfig config) {
        all = singletonList(new GeocodeSuggestionsGMapsExtension(config));
    }

    @Override
    public Collection<SdkExtension> getAll() {
        return all.stream().map(e -> (SdkExtension) e).collect(toSet());
    }
}
