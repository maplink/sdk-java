package global.maplink.place.schema;

import global.maplink.json.JsonMapper;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.DayOfWeek;

import static global.maplink.place.testUtils.SampleFiles.OPENHOURTIMEDAY;
import static global.maplink.place.testUtils.SampleFiles.PLACEROUTE;
import static org.junit.jupiter.api.Assertions.*;

public class OpenHourTimeDayTest {

    private final JsonMapper mapper = JsonMapper.loadDefault();

    @Test
    public void shouldSerialize(){
        OpenHourTimeDay openHourTimeDay = mapper.fromJson(OPENHOURTIMEDAY.load(), OpenHourTimeDay.class);
        assertEquals("08:00", openHourTimeDay.getTime());
        assertEquals(DayOfWeek.MONDAY, openHourTimeDay.getDay());
    }
}
