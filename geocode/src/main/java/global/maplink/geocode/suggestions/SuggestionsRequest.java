package global.maplink.geocode.suggestions;

import global.maplink.geocode.common.Type;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Builder
@Getter
@ToString
@EqualsAndHashCode
public class SuggestionsRequest {

    private final String query;

    private final Type type = null;
}
