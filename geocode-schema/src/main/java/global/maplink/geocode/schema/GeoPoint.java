package global.maplink.geocode.schema;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor(force = true)
@AllArgsConstructor
public class GeoPoint {

    private final BigDecimal lat;
    private final BigDecimal lon;
}