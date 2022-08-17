package global.maplink.geocode.schema;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor(force = true)
@AllArgsConstructor
public class Address {

    private final String road;
    private final String number;
    private final String district;
    private final String zipCode;
    private final String city;
    private final State state;
    private final String country;
    private final String type;
    private final Boolean capital;
    private final Double area;
    private final IBGECode ibge;

    private final GeoPoint mainLocation;
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