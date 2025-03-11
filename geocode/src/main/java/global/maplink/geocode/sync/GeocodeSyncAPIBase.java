package global.maplink.geocode.sync;

import global.maplink.geocode.schema.v2.structured.StructuredRequest;
import global.maplink.geocode.schema.v2.suggestions.SuggestionsBaseRequest;
import global.maplink.geocode.schema.v1.suggestions.SuggestionsResult;

public interface GeocodeSyncAPIBase {

    SuggestionsResult suggestions(SuggestionsBaseRequest request);

    SuggestionsResult structured(StructuredRequest request);

}
