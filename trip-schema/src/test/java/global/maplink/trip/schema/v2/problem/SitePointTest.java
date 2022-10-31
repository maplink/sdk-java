package global.maplink.trip.schema.v2.problem;

import global.maplink.json.JsonMapper;
import global.maplink.trip.schema.v2.problem.SitePoint;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static global.maplink.trip.testUtils.ProblemSampleFiles.SITE_POINT;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class SitePointTest {

    private final JsonMapper mapper = JsonMapper.loadDefault();

    @Test
    public void shouldSerialize(){
        SitePoint sitePoint = mapper.fromJson(SITE_POINT.load(), SitePoint.class);
        assertEquals("36cdd555-41b6-4327-ac83-04ac74cff915", sitePoint.getSiteId());
        assertEquals(0, new BigDecimal("-23.5666499").compareTo(sitePoint.getLatitude()));
        assertEquals(0, new BigDecimal("-46.6557755").compareTo(sitePoint.getLongitude()));
    }
}
