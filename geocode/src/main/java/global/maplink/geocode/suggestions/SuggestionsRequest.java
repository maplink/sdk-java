package global.maplink.geocode.suggestions;

import global.maplink.geocode.common.Type;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class SuggestionsRequest {

    private final String query;

    private final Type type = null;
}
