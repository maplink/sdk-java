package global.maplink.geocode.schema;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor(force = true)
@AllArgsConstructor(staticName = "of")
public class GeoPoint {

    private final BigDecimal lat;
    private final BigDecimal lon;

    public static GeoPoint of(double lat, double lon) {
        return GeoPoint.of(
                BigDecimal.valueOf(lat),
                BigDecimal.valueOf(lon)
        );
    }
}