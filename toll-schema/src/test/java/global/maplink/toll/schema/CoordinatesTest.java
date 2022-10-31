package global.maplink.toll.schema;

import gloabl.maplink.toll.schema.Coordinates;
import gloabl.maplink.toll.schema.TollServiceType;
import global.maplink.json.JsonMapper;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static global.maplink.toll.testUtils.SampleFiles.COORDINATES;
import static global.maplink.toll.testUtils.SampleFiles.TOLL_SERVICE_TYPE;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class CoordinatesTest {

    private final JsonMapper mapper = JsonMapper.loadDefault();

    @Test
    public void shouldDeserialize(){
        Coordinates coordinates = mapper.fromJson(COORDINATES.load(), Coordinates.class);
        assertEquals(0, new BigDecimal("-23.5666499").compareTo(coordinates.getLatitude()));
        assertEquals(0, new BigDecimal("-46.6557755").compareTo(coordinates.getLongitude()));
    }
}
