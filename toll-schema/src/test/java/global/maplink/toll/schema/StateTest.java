package global.maplink.toll.schema;

import gloabl.maplink.toll.schema.State;
import gloabl.maplink.toll.schema.TollServiceType;
import global.maplink.json.JsonMapper;
import org.junit.jupiter.api.Test;

import static global.maplink.toll.testUtils.SampleFiles.STATE;
import static global.maplink.toll.testUtils.SampleFiles.TOLL_SERVICE_TYPE;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class StateTest {

    private final JsonMapper mapper = JsonMapper.loadDefault();

    @Test
    public void shouldDeserialize(){
        State state = mapper.fromJson(STATE.load(), State.class);
        assertEquals("Sao Paulo", state.getName());
        assertEquals("SP", state.getCode());
    }
}
