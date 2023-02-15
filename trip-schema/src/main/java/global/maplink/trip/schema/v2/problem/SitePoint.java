package global.maplink.trip.schema.v2.problem;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;

import static lombok.AccessLevel.PRIVATE;

@Data
@Builder
@RequiredArgsConstructor
@NoArgsConstructor(force = true, access = PRIVATE)
public class SitePoint {
    private final String siteId;
    private final BigDecimal latitude;
    private final BigDecimal longitude;

    public boolean equalsLatLong(final SitePoint other) {
        if (this == other) {
            return true;
        }

        if (other == null) {
            return false;
        }

        return this.getLatitude().compareTo(other.getLatitude()) == 0
                && this.getLongitude().compareTo(other.getLongitude()) == 0;
    }

    public static SitePoint of(double lat, double lon) {
        return of(null, lat, lon);
    }

    public static SitePoint of(String id, double lat, double lon) {
        return new SitePoint(id, BigDecimal.valueOf(lat), BigDecimal.valueOf(lon));
    }
}
