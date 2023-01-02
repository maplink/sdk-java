package global.maplink.place.schema;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

import static lombok.AccessLevel.PRIVATE;

@Data
@Builder
@RequiredArgsConstructor
@NoArgsConstructor(force = true, access = PRIVATE)
public class Leg {
    @Singular
    private final List<Point> points;

    public static Leg of(double latitude, double longitude, double... points) {
        if (points.length % 2 != 0) {
            throw new IllegalArgumentException("Leg.points requires pairs of lat long");
        }
        List<Point> list = new ArrayList<>(points.length / 2);
        list.add(Point.of(latitude, longitude));
        for (int i = 0; i < points.length; i += 2) {
            list.add(Point.of(points[i], points[i + 1]));
        }
        return new Leg(list);
    }
}
