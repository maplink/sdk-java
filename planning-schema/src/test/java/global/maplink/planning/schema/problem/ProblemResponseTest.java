package global.maplink.planning.schema.problem;

import global.maplink.json.JsonMapper;
import org.junit.jupiter.api.Test;

import static global.maplink.planning.testUtils.SampleFiles.PROBLEM_RESPONSE;
import static org.junit.jupiter.api.Assertions.assertEquals;

class ProblemResponseTest {

    private final JsonMapper mapper = JsonMapper.loadDefault();

    @Test
    void shouldDeserialize(){

        ProblemResponse problemResponse = mapper.fromJson(PROBLEM_RESPONSE.load(), ProblemResponse.class);

        assertEquals("exemplo1", problemResponse.getId());
    }
}

