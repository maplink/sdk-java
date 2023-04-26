package global.maplink.place.schema;

import global.maplink.domain.MaplinkPoints;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import static lombok.AccessLevel.PRIVATE;

@Data
@Builder
@RequiredArgsConstructor
@NoArgsConstructor(force = true, access = PRIVATE)
public class Leg {
    private final MaplinkPoints points;

    public static Leg of(double... points) {
        return new Leg(MaplinkPoints.from(points));
    }
}
