package global.maplink.geocode.schema.v1.suggestions;

import global.maplink.geocode.schema.SuggestionBase;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Singular;
import lombok.experimental.SuperBuilder;

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
import static java.util.stream.Collectors.toList;

@SuppressWarnings("ConstantConditions")
@Data
@AllArgsConstructor
@NoArgsConstructor(force = true)
@SuperBuilder
public class SuggestionsResult implements Iterable<SuggestionBase> {

    public static final SuggestionsResult EMPTY = new SuggestionsResult(0, emptyList());

    private final int found;

    @Singular
    private final List<SuggestionBase> results;

    public boolean isEmpty() {
        return results == null || results.isEmpty();
    }

    public SuggestionBase getMostRelevant() {
        if (isEmpty()) return null;
        return results.stream().max(naturalOrder()).orElse(results.get(0));
    }

    public Optional<SuggestionBase> getById(String id) {
        requireNonNull(id);
        if (isEmpty()) return empty();

        return results.stream().filter(r -> id.equals(r.getId())).findAny();
    }

    public SuggestionsResult joinTo(SuggestionsResult anotherResult) {
        return new SuggestionsResult(
                found + anotherResult.found,
                Stream.concat(stream(), anotherResult.stream()).collect(toList())
        );
    }

    @Override
    public Iterator<SuggestionBase> iterator() {
        if (isEmpty()) {
            return emptyIterator();
        }
        return results.iterator();
    }

    public Stream<SuggestionBase> stream() {
        return StreamSupport.stream(this.spliterator(), false);
    }

    public Stream<SuggestionBase> parallelStream() {
        return StreamSupport.stream(this.spliterator(), true);
    }
}
