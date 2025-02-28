package global.maplink.geocode.schema.v2;


import global.maplink.domain.MaplinkPoint;
import global.maplink.geocode.schema.AddressBase;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@NoArgsConstructor(force = true)
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
public class Address extends AddressBase {

    private final MaplinkPoint mainLocation;


}
