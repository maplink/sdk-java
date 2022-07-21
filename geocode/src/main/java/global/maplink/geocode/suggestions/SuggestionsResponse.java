package global.maplink.geocode.suggestions;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor(force = true)
public class SuggestionsResponse {

    private final int found;

    private final List<Suggestion> results;

    public Suggestion getMostRelevant() {
        if (results == null || results.isEmpty()) return null;
        return results.stream().sorted().findFirst().orElse(null);
    }
}
