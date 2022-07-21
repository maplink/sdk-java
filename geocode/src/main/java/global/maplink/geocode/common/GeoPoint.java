package global.maplink.geocode.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor(force = true)
@AllArgsConstructor
public class GeoPoint {

    private final Double lat;
    private final Double lon;
}