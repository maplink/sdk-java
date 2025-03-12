package global.maplink.geocode.schema.suggestions;

import global.maplink.geocode.schema.PointOfInterest;
import global.maplink.geocode.schema.Address;
import global.maplink.geocode.schema.Type;
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
    private final PointOfInterest poi;
    private final Address address;
    private final Type type;
    private final Float score;
    private final Double distance;
    private final String polygonWKT;
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