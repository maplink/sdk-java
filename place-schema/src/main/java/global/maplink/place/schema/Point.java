package global.maplink.place.schema;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;

import static lombok.AccessLevel.PRIVATE;

@Data
@Builder
@RequiredArgsConstructor(staticName = "of")
@NoArgsConstructor(force = true, access = PRIVATE)
public class Point {
    private final BigDecimal latitude;
    private final BigDecimal longitude;

    public static Point of(double latitude, double longitude) {
        return Point.of(BigDecimal.valueOf(latitude), BigDecimal.valueOf(longitude));
    }
}
