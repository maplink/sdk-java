package global.maplink.geocode.schema.v2.suggestions;

import global.maplink.geocode.schema.SuggestionBase;
import global.maplink.geocode.schema.v2.Address;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@NoArgsConstructor(force = true)
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
public class Suggestion extends SuggestionBase {
    @Override
    public Address getAddress() {
        return (Address) super.getAddress();
    }
}