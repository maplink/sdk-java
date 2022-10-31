package global.maplink.place.schema;

import global.maplink.json.JsonMapper;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static global.maplink.place.testUtils.SampleFiles.POINT;
import static global.maplink.place.testUtils.SampleFiles.SOCIALCONNECTION;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class PointTest {

    private final JsonMapper mapper = JsonMapper.loadDefault();

    @Test
    public void shouldDeserialize(){
        Point point = mapper.fromJson(POINT.load(), Point.class);
        assertEquals(0, new BigDecimal("-23.5666499").compareTo(point.getLatitude()));
        assertEquals(0, new BigDecimal("-46.6557755").compareTo(point.getLongitude()));
    }
}
