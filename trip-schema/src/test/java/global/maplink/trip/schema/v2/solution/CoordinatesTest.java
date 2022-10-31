package global.maplink.trip.schema.v2.solution;

import global.maplink.json.JsonMapper;
import global.maplink.trip.schema.v2.solution.Coordinates;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static global.maplink.trip.testUtils.SolutionSampleFiles.COORDINATES;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class CoordinatesTest {

    private final JsonMapper mapper = JsonMapper.loadDefault();

    @Test
    public void shouldSerialize(){
        Coordinates coordinates = mapper.fromJson(COORDINATES.load(), Coordinates.class);
        assertEquals(0, new BigDecimal("-23.5666499").compareTo(coordinates.getLatitude()));
        assertEquals(0, new BigDecimal("-46.6557755").compareTo(coordinates.getLongitude()));
    }
}
