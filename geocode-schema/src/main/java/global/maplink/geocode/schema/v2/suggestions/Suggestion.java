package global.maplink.geocode.schema.v2.suggestions;

import global.maplink.geocode.schema.v2.AddressBase;
import global.maplink.geocode.schema.v1.Type;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor(force = true)
@AllArgsConstructor
@Builder
public class Suggestion implements Comparable<Suggestion> {
    private final String id;
    private final AddressBase address;
    private final Type type;
    private final Double score;
    private final Double distance;
    private final String label;

    @Override
    public int compareTo(Suggestion o) {
        if (score == null) {
            return o.score == null ? 0 : -1;
        }
        if (o.score == null) {
            return 1;
        }
        return Double.compare(score, o.score);
    }
}