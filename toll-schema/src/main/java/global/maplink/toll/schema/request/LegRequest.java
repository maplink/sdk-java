package global.maplink.toll.schema.request;

import global.maplink.toll.schema.Coordinates;
import global.maplink.toll.schema.TollVehicleType;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LegRequest {

    private TollVehicleType vehicleType;

    @Singular
    private List<Coordinates> points;

    public List<Coordinates> getPoints() {
        if (this.points == null) {
            this.points = new ArrayList<>();
        }
        return this.points;
    }
}
