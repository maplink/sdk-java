package global.maplink.place.schema;

import global.maplink.json.JsonMapper;
import global.maplink.place.testUtils.SampleFiles;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static global.maplink.place.testUtils.SampleFiles.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class LegTest {

    private final JsonMapper mapper = JsonMapper.loadDefault();

    @Test
    public void shouldSerialize(){
        Leg leg = mapper.fromJson(LEG.load(), Leg.class);
        assertEquals(3, leg.getPoints().size());
        assertEquals(0, new BigDecimal("-23.5666499").compareTo(leg.getPoints().get(0).getLatitude()));
        assertEquals(0, new BigDecimal("-46.6557755").compareTo(leg.getPoints().get(0).getLongitude()));
        assertEquals(0, new BigDecimal("-23.5688742").compareTo(leg.getPoints().get(1).getLatitude()));
        assertEquals(0, new BigDecimal("-46.6638823").compareTo(leg.getPoints().get(1).getLongitude()));
        assertEquals(0, new BigDecimal("-23.5757982").compareTo(leg.getPoints().get(2).getLatitude()));
        assertEquals(0, new BigDecimal("-46.6779297").compareTo(leg.getPoints().get(2).getLongitude()));
    }
}
