package global.maplink.planning.schema.problem;

import global.maplink.json.JsonMapper;
import org.junit.jupiter.api.Test;

import static global.maplink.planning.testUtils.SampleFiles.LOGISTIC_ZONE;
import static org.assertj.core.api.Assertions.assertThat;

public class LogisticZoneTest {

    private final JsonMapper mapper = JsonMapper.loadDefault();

    @Test
    void shouldDeserialize(){

        LogisticZone logisticZone = mapper.fromJson(LOGISTIC_ZONE.load(), LogisticZone.class);

        assertThat(logisticZone.getName()).isEqualTo("exemplo1");
        assertThat(logisticZone.getZonePriority()).isEqualTo("exemplo2");
    }
}

