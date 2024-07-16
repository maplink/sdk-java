package global.maplink.planning.schema.problem;

import global.maplink.json.JsonMapper;
import org.junit.jupiter.api.Test;

import static global.maplink.planning.testUtils.ProblemSampleFiles.TIME_WINDOW;
import static org.junit.jupiter.api.Assertions.assertEquals;

class TimeWindowTest {

    private final JsonMapper mapper = JsonMapper.loadDefault();

    @Test
    void shouldDeserialize(){

        TimeWindow timeWindow = mapper.fromJson(TIME_WINDOW.load(), TimeWindow.class);

        assertEquals(10, timeWindow.getStart());
        assertEquals(20, timeWindow.getEnd());
    }
}

