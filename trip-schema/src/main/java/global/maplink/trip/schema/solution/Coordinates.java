package global.maplink.trip.schema.solution;

import lombok.*;

import java.math.BigDecimal;

import static lombok.AccessLevel.PRIVATE;

@Data
@Builder
@RequiredArgsConstructor
@NoArgsConstructor(force = true, access = PRIVATE)
public class Coordinates {
    private final BigDecimal latitude;
    private final BigDecimal longitude;
}
