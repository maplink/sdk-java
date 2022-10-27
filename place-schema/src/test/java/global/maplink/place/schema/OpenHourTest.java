package global.maplink.place.schema;

import global.maplink.json.JsonMapper;
import org.junit.jupiter.api.Test;

import java.time.DayOfWeek;

import static global.maplink.place.testUtils.SampleFiles.OPENHOUR;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class OpenHourTest {

    private final JsonMapper mapper = JsonMapper.loadDefault();

    @Test
    public void shouldSerialize(){
        OpenHour openHour = mapper.fromJson(OPENHOUR.load(), OpenHour.class);
        assertEquals("09:00", openHour.getOpen().getTime());
        assertEquals(DayOfWeek.MONDAY, openHour.getOpen().getDay());
        assertEquals("18:00", openHour.getClose().getTime());
        assertEquals(DayOfWeek.MONDAY, openHour.getClose().getDay());
    }
}
