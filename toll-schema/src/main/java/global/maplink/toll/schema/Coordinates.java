package global.maplink.toll.schema;

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
public class Coordinates {
    private final BigDecimal latitude;
    private final BigDecimal longitude;

    public static Coordinates of(double latitude, double longitude) {
        return of(BigDecimal.valueOf(latitude), BigDecimal.valueOf(longitude));
    }

}
