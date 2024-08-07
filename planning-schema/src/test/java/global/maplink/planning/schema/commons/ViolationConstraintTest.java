package global.maplink.planning.schema.commons;

import global.maplink.json.JsonMapper;
import org.junit.jupiter.api.Test;

import static global.maplink.planning.testUtils.CommonSampleFiles.VIOLATION_CONSTRAINT;
import static org.junit.jupiter.api.Assertions.assertEquals;

class ViolationConstraintTest {

    private final JsonMapper mapper = JsonMapper.loadDefault();

    @Test
    void shouldDeserialize(){

        ViolationConstraint violationConstraint = mapper.fromJson(VIOLATION_CONSTRAINT.load(), ViolationConstraint.class);

        assertEquals("exemplo1", violationConstraint.getMessage());
    }
}

