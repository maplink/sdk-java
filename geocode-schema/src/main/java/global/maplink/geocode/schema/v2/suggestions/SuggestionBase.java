package global.maplink.geocode.schema.v2.suggestions;

import global.maplink.geocode.schema.Type;
import global.maplink.geocode.schema.v2.AddressBase;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@NoArgsConstructor(force = true)
@AllArgsConstructor
@SuperBuilder
public class SuggestionBase implements Comparable<SuggestionBase> {
    private final String id;
    private final AddressBase address;
    private final Type type;
    private final Float score;
    private final Double distance;
    private final String label;

    @Override
    public int compareTo(SuggestionBase o) {
        if (score == null) {
            return o.score == null ? 0 : -1;
        }
        if (o.score == null) {
            return 1;
        }
        return Double.compare(score, o.score);
    }
}