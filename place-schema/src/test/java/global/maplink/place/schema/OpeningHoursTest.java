package global.maplink.place.schema;

import global.maplink.json.JsonMapper;
import org.junit.jupiter.api.Test;

import java.time.DayOfWeek;

import static global.maplink.place.testUtils.SampleFiles.OPENHOURTIMEDAY;
import static global.maplink.place.testUtils.SampleFiles.OPENINGHOURS;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class OpeningHoursTest {

    private final JsonMapper mapper = JsonMapper.loadDefault();

    @Test
    public void shouldDeserialize(){
        OpeningHours openingHours = mapper.fromJson(OPENINGHOURS.load(), OpeningHours.class);
        assertEquals(2, openingHours.getPeriods().size());
        assertEquals("09:00", openingHours.getPeriods().get(0).getOpen().getTime());
        assertEquals(DayOfWeek.MONDAY, openingHours.getPeriods().get(0).getOpen().getDay());
        assertEquals("12:00", openingHours.getPeriods().get(0).getClose().getTime());
        assertEquals(DayOfWeek.MONDAY, openingHours.getPeriods().get(0).getClose().getDay());
        assertEquals("13:00", openingHours.getPeriods().get(1).getOpen().getTime());
        assertEquals(DayOfWeek.MONDAY, openingHours.getPeriods().get(1).getOpen().getDay());
        assertEquals("18:00", openingHours.getPeriods().get(1).getClose().getTime());
        assertEquals(DayOfWeek.MONDAY, openingHours.getPeriods().get(1).getClose().getDay());
    }
}
