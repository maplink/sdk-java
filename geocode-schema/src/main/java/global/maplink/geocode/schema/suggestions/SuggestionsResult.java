package global.maplink.geocode.schema.suggestions;

import lombok.*;

import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import static java.util.Collections.emptyIterator;
import static java.util.Collections.emptyList;
import static java.util.Comparator.naturalOrder;
import static java.util.Objects.requireNonNull;
import static java.util.Optional.empty;

@SuppressWarnings("ConstantConditions")
@Data
@AllArgsConstructor
@NoArgsConstructor(force = true)
@Builder
public class SuggestionsResult implements Iterable<Suggestion> {

    public static final SuggestionsResult EMPTY = new SuggestionsResult(0, emptyList());

    private final int found;

    @Singular
    private final List<Suggestion> results;

    public boolean isEmpty() {
        return results == null || results.isEmpty();
    }

    public Suggestion getMostRelevant() {
        if (isEmpty()) return null;
        return results.stream().max(naturalOrder()).orElse(results.get(0));
    }

    public Optional<Suggestion> getById(String id) {
        requireNonNull(id);
        if (isEmpty()) return empty();

        return results.stream().filter(r -> id.equals(r.getId())).findAny();
    }

    @Override
    public Iterator<Suggestion> iterator() {
        if (isEmpty()) {
            return emptyIterator();
        }
        return results.iterator();
    }

    public Stream<Suggestion> stream() {
        return StreamSupport.stream(this.spliterator(), false);
    }

    public Stream<Suggestion> parallelStream() {
        return StreamSupport.stream(this.spliterator(), true);
    }
}
