package global.maplink.geocode.sync;

import global.maplink.geocode.schema.v2.structured.StructuredRequest;
import global.maplink.geocode.schema.v1.suggestions.SuggestionsResult;

public interface GeocodeSyncAPIBase {

    SuggestionsResult structured(StructuredRequest request);

}
