package global.maplink.trip.schema.problem;

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
}
