package global.maplink.toll.schema.request;

import global.maplink.toll.schema.Condition;
import global.maplink.toll.schema.Coordinates;
import global.maplink.toll.schema.TollVehicleType;
import lombok.*;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LegRequest {

    @Builder.Default
    private Long calculationDate = Instant.now().toEpochMilli();

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

    public static LegRequest of(TollVehicleType vehicleType, double... coordinates) {
        return LegRequest.builder()
                .vehicleType(vehicleType)
                .points(parseCoordinates(coordinates))
                .build();
    }

    public static LegRequest of(Long calculationDate, TollVehicleType vehicleType, Condition condition, double... coordinates) {
        return LegRequest.builder()
                .calculationDate(calculationDate)
                .vehicleType(vehicleType)
                .condition(condition)
                .points(parseCoordinates(coordinates))
                .build();
    }

    private static List<Coordinates> parseCoordinates(double... coordinates) {
        if (coordinates.length % 2 != 0) {
            throw new IllegalArgumentException("Coordinates must be provided in pairs lat,lon");
        }

        List<Coordinates> listCoordinates = new ArrayList<>(coordinates.length / 2);
        for (int i = 0; i < coordinates.length; i += 2) {
            listCoordinates.add(Coordinates.of(coordinates[i], coordinates[i + 1]));
        }
        return listCoordinates;
    }

}