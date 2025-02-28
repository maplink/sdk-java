package global.maplink.geocode.schema;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@NoArgsConstructor(force = true)
@AllArgsConstructor
@SuperBuilder
public class AddressBase {

    private final String road;
    private final String number;
    private final String district;
    private final String zipCode;
    private final String city;
    private final State state;

}