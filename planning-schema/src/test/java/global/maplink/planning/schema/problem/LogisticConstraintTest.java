package global.maplink.planning.schema.problem;

import global.maplink.json.JsonMapper;
import org.junit.jupiter.api.Test;

import static global.maplink.planning.testUtils.SampleFiles.LOGISTIC_CONSTRAINT;
import static org.assertj.core.api.Assertions.assertThat;

class LogisticConstraintTest {

    private final JsonMapper mapper = JsonMapper.loadDefault();

    @Test
    void shouldDeserialize(){

        LogisticConstraint logisticConstraint = mapper.fromJson(LOGISTIC_CONSTRAINT.load(), LogisticConstraint.class);

        assertThat(logisticConstraint.getName()).isEqualTo("exemplo1");
        assertThat(logisticConstraint.getLoadingPositionInTimeWindow()).isEqualTo("exemplo2");
        assertThat(logisticConstraint.getUnloadingPositionInTimeWindow()).isEqualTo("exemplo3");
        assertThat(logisticConstraint.getLoadingPositionInRoute()).isEqualTo("exemplo4");
        assertThat(logisticConstraint.getUnloadingPositionInRoute()).isEqualTo("exemplo5");
        assertThat(logisticConstraint.getSiteUnloadingFixedTime()).isEqualTo(10);
        assertThat(logisticConstraint.getSiteLoadingFixedTime()).isEqualTo(11);
        assertThat(logisticConstraint.getLoadingMaxSize()).isEqualTo(12);
        assertThat(logisticConstraint.getUnloadingMaxSize()).isEqualTo(13);
        assertThat(logisticConstraint.getLoadingTimeFlow()).isEqualTo(14.0);
        assertThat(logisticConstraint.getUnloadingTimeFlow()).isEqualTo(15.0);
    }
}

