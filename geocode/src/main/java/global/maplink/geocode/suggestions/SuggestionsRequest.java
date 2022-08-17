package global.maplink.geocode.suggestions;

import global.maplink.geocode.schema.Type;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class SuggestionsRequest {

    private final String query;

    private Type type;
}
