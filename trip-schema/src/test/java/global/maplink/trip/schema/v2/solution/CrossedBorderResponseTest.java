package global.maplink.trip.schema.v2.solution;

import global.maplink.json.JsonMapper;
import global.maplink.trip.schema.v2.solution.CrossedBorderResponse;
import lombok.Data;
import org.junit.jupiter.api.Test;

import static global.maplink.trip.testUtils.SolutionSampleFiles.CROSSED_BORDER_RESPONSE;
import static org.junit.jupiter.api.Assertions.assertEquals;

@Data
public class CrossedBorderResponseTest {

    private final JsonMapper mapper = JsonMapper.loadDefault();

    @Test
    public void shouldDeserialize(){
        CrossedBorderResponse crossedBorderResponse = mapper.fromJson(CROSSED_BORDER_RESPONSE.load(), CrossedBorderResponse.class);
        assertEquals("Sao Paulo", crossedBorderResponse.getCity());
        assertEquals("SP", crossedBorderResponse.getState());
        assertEquals("Brasil", crossedBorderResponse.getCountry());
    }
}
