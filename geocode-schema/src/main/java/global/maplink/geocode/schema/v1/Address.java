package global.maplink.geocode.schema.v1;

import global.maplink.geocode.schema.v2.AddressBase;
import global.maplink.geocode.schema.GeoPoint;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Data
@NoArgsConstructor(force = true)
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
public class Address extends AddressBase {

    private final String country;
    private final String type;
    private final Boolean capital;
    private final Double area;
    private final IBGECode ibge;

    private final List<GeoPoint> otherLocations;
    private final List<GeoPoint> geometry;

    private final String leftZipCode;
    private final String rightZipCode;
    private final Integer leftFirstNumber;
    private final Integer leftLastNumber;
    private final Integer rightFirstNumber;
    private final Integer rightLastNumber;

    private final String direction;
    private final String acronym;
}