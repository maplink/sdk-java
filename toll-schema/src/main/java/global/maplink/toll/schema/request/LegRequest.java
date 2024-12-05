package global.maplink.toll.schema.request;

import global.maplink.toll.schema.Condition;
import global.maplink.toll.schema.Coordinates;
import global.maplink.toll.schema.TollVehicleType;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

import static java.util.Collections.emptyList;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LegRequest {

    @Builder.Default
    private Long calculationDate = System.currentTimeMillis();

    private TollVehicleType vehicleType;

    private Condition condition = new Condition();

    @Singular
    private List<Coordinates> points;

    public List<Coordinates> getPoints() {
        if (this.points == null) {
            this.points = new ArrayList<>();
        }
        return this.points;
    }

    public static LegRequest of(Long calculationDate, TollVehicleType vehicleType, Condition condition, double... coordinates) {
        if (coordinates.length % 2 != 0) {
            throw new IllegalArgumentException("Coordinates must be provided in pairs lat,lon");
        }
        if (coordinates.length == 0) {
            return new LegRequest(calculationDate, vehicleType, condition, emptyList());
        }

        List<Coordinates> list = new ArrayList<>(coordinates.length / 2);
        for (int i = 0; i < coordinates.length; i += 2) {
            list.add(Coordinates.of(coordinates[i], coordinates[i + 1]));
        }
        return new LegRequest(calculationDate, vehicleType, condition, emptyList());

    }

}
