package global.maplink.planning.schema.problem;

import global.maplink.json.JsonMapper;
import org.junit.jupiter.api.Test;

import static global.maplink.planning.schema.problem.LogisticZoneType.PRIORITARY;
import static global.maplink.planning.testUtils.ProblemSampleFiles.LOGISTIC_ZONE;
import static org.assertj.core.api.Assertions.assertThat;

class LogisticZoneTest {

    private final JsonMapper mapper = JsonMapper.loadDefault();

    @Test
    void shouldDeserialize(){

        LogisticZone logisticZone = mapper.fromJson(LOGISTIC_ZONE.load(), LogisticZone.class);

        assertThat(logisticZone.getName()).isEqualTo("exemplo1");
        assertThat(logisticZone.getZonePriority()).isEqualTo(PRIORITARY);
    }
}

