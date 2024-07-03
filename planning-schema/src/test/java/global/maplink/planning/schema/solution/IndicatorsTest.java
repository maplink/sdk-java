package global.maplink.planning.schema.solution;

import global.maplink.json.JsonMapper;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static global.maplink.planning.testUtils.SampleFiles.INDICATORS;
import static org.assertj.core.api.Assertions.assertThat;


class IndicatorsTest {

    private final JsonMapper mapper = JsonMapper.loadDefault();

    @Test
    void shouldDeserialize(){

        Indicators indicators = mapper.fromJson(INDICATORS.load(), Indicators.class);

        assertThat(indicators.getTotalServiceTime()).isEqualTo(1);
        assertThat(indicators.getTotalDeliveringTime()).isEqualTo(2);
        assertThat(indicators.getDayWorkingTotalTime()).isEqualTo(3);
        assertThat(indicators.getNightWorkingTotalTime()).isEqualTo(4);
        assertThat(indicators.getTotalUnloadingTime()).isEqualTo(5);
        assertThat(indicators.getTotalWorkingTime()).isEqualTo(6);
        assertThat(indicators.getTotalCollectingTime()).isEqualTo(7);
        assertThat(indicators.getTimeWindowNumber()).isEqualTo(8);
        assertThat(indicators.getTotalDrivingTime()).isEqualTo(9);
        assertThat(indicators.getTotalLoadingTime()).isEqualTo(10);
        assertThat(indicators.getTotalTime()).isEqualTo(11);
        assertThat(indicators.getTotalDistance()).isEqualTo(12);
        assertThat(indicators.getRejectOperationsNumber()).isEqualTo(13);
        assertThat(indicators.getTotalWaitingTime()).isEqualTo(14);
        assertThat(indicators.getTotalRestTime()).isEqualTo(15);
        assertThat(indicators.getRoutesNumber()).isEqualTo(16);
        assertThat(indicators.getAverageOccupancyRateVolume()).isEqualTo(17.0);
        assertThat(indicators.getAverageOccupancyRateWeight()).isEqualTo(18.0);
        BigDecimal bigDecimalTest = BigDecimal.valueOf(19);
        assertThat(indicators.getTollCosts().compareTo(bigDecimalTest)).isEqualTo(0);
    }
}