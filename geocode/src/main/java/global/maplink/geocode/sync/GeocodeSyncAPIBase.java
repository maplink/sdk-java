package global.maplink.geocode.sync;

import global.maplink.geocode.schema.v2.suggestions.SuggestionsResult;
import global.maplink.geocode.schema.v2.reverse.ReverseBaseRequest;
import global.maplink.geocode.schema.v2.structured.StructuredBaseRequest;
import global.maplink.geocode.schema.v2.suggestions.SuggestionsBaseRequest;

public interface GeocodeSyncAPIBase {

    SuggestionsResult suggestions(SuggestionsBaseRequest request);

    SuggestionsResult structured(StructuredBaseRequest request);

    SuggestionsResult reverse(ReverseBaseRequest request);
}
