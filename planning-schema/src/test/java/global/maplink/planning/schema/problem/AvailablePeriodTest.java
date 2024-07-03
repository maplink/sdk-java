package global.maplink.planning.schema.problem;

import global.maplink.json.JsonMapper;
import org.junit.jupiter.api.Test;

import static global.maplink.planning.testUtils.SampleFiles.AVAILABLE_PERIOD;
import static org.assertj.core.api.Assertions.assertThat;

class AvailablePeriodTest {

    private final JsonMapper mapper = JsonMapper.loadDefault();

    @Test
    void shouldDeserialize(){

        AvailablePeriod availablePeriod = mapper.fromJson(AVAILABLE_PERIOD.load(), AvailablePeriod.class);

        assertThat(availablePeriod.getTimeWindow().getStart()).isEqualTo(1702897200000L);
        assertThat(availablePeriod.getTimeWindow().getEnd()).isEqualTo(1703538000000L);
        assertThat(availablePeriod.getMaxRoutesNumber()).isEqualTo(20);
        assertThat(availablePeriod.getMaxWorkingTime()).isEqualTo(3);
        assertThat(availablePeriod.getMaxDrivingTime()).isEqualTo(4);
    }
}
