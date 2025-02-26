package global.maplink.geocode.schema.v1.suggestions;

import global.maplink.geocode.schema.v1.Address;
import global.maplink.geocode.schema.v1.PointOfInterest;
import global.maplink.geocode.schema.v2.suggestions.SuggestionBase;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@NoArgsConstructor(force = true)
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
public class Suggestion extends SuggestionBase {
    private final PointOfInterest poi;
    private final String polygonWKT;

    @Override
    public Address getAddress() {
        return (Address) super.getAddress();
    }
}