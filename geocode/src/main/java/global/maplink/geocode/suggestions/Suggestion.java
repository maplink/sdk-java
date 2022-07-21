package global.maplink.geocode.suggestions;

import global.maplink.geocode.common.Address;
import global.maplink.geocode.common.PointOfInterest;
import global.maplink.geocode.common.Type;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import static java.util.Comparator.comparingDouble;

@Data
@NoArgsConstructor(force = true)
@AllArgsConstructor
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
        return comparingDouble(Suggestion::getScore).reversed().compare(this, o);
    }
}