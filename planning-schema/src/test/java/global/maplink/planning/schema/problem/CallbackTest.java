package global.maplink.planning.schema.problem;

import global.maplink.json.JsonMapper;
import org.junit.jupiter.api.Test;

import static global.maplink.planning.testUtils.SampleFiles.CALLBACK;
import static org.junit.jupiter.api.Assertions.assertEquals;


class CallbackTest {

    private final JsonMapper mapper = JsonMapper.loadDefault();

    @Test
    void shouldDeserialize(){

        Callback callback = mapper.fromJson(CALLBACK.load(), Callback.class);

        assertEquals("exemplo1", callback.getJobId());
        assertEquals("www.qwerty.com", callback.getUrl());
        assertEquals("guilherme", callback.getUser());
        assertEquals("qwerty", callback.getPassword());
    }
}