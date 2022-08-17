package global.maplink.geocode.schema.suggestions;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Optional;

import static java.util.Objects.requireNonNull;
import static java.util.Optional.empty;

@SuppressWarnings("ConstantConditions")
@Data
@AllArgsConstructor
@NoArgsConstructor(force = true)
public class SuggestionsResult {

    private final int found;

    private final List<Suggestion> results;

    public boolean isEmpty() {
        return results == null || results.isEmpty();
    }

    public Suggestion getMostRelevant() {
        if (isEmpty()) return null;
        return results.stream().sorted().findFirst().orElse(null);
    }

    public Optional<Suggestion> getById(String id) {
        requireNonNull(id);
        if (isEmpty()) return empty();

        return results.stream().filter(r -> id.equals(r.getId())).findAny();
    }
}
