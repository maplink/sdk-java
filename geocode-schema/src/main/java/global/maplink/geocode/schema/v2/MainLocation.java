package global.maplink.geocode.schema.v2;

import global.maplink.geocode.schema.GeoPoint;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor(force = true)
@AllArgsConstructor
public class MainLocation {

    private Integer radius;
    private GeoPoint center;
}
