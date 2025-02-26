package global.maplink.geocode.schema.v2;

import global.maplink.geocode.schema.GeoPoint;
import global.maplink.geocode.schema.State;
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

    private final GeoPoint mainLocation;


}