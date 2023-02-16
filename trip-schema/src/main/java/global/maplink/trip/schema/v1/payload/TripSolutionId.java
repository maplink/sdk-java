package global.maplink.trip.schema.v1.payload;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@NoArgsConstructor(force = true)
public class TripSolutionId {
    private final String id;
}
