package global.maplink.planning.schema.problem;

import global.maplink.json.JsonMapper;
import org.junit.jupiter.api.Test;

import static global.maplink.planning.testUtils.SampleFiles.VIOLATION_CONSTRAINT;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ViolationConstraintTest {

    private final JsonMapper mapper = JsonMapper.loadDefault();

    @Test
    void shouldDeserialize(){

        ViolationConstraint violationConstraint = mapper.fromJson(VIOLATION_CONSTRAINT.load(), ViolationConstraint.class);

        assertEquals("exemplo1", violationConstraint.getMessage());
    }
}

