package global.maplink.tracking.schema.schema.domain;


import global.maplink.geocode.schema.GeoPoint;
import lombok.*;

@Data
@Builder
@NoArgsConstructor(force = true)
@AllArgsConstructor
public class Driver {

    private final String name;
    private final String image;
    private final GeoPoint currentLocation;
}
