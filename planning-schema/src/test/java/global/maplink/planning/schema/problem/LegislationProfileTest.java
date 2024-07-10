package global.maplink.planning.schema.problem;

import global.maplink.json.JsonMapper;
import org.junit.jupiter.api.Test;

import static global.maplink.planning.testUtils.ProblemSampleFiles.LEGISLATION_PROFILE;
import static org.assertj.core.api.Assertions.assertThat;

class LegislationProfileTest {

    private final JsonMapper mapper = JsonMapper.loadDefault();

    @Test
    void shouldDeserialize(){

        LegislationProfile legislationProfile = mapper.fromJson(LEGISLATION_PROFILE.load(), LegislationProfile.class);

        assertThat(legislationProfile.getName()).isEqualTo("exemplo1");
        assertThat(legislationProfile.getMaxContinuousDrivingTime()).isEqualTo(10);
        assertThat(legislationProfile.getDrivingPauseDuration()).isEqualTo(11);
        assertThat(legislationProfile.getMaxContinuousWorkingTime()).isEqualTo(12);
        assertThat(legislationProfile.getWorkingPauseDuration()).isEqualTo(13);
        assertThat(legislationProfile.getMaxDrivingTimeBetweenTwoRests()).isEqualTo(14);
        assertThat(legislationProfile.getDrivingRestDuration()).isEqualTo(15);
        assertThat(legislationProfile.getMaxWorkingTimeBetweenTwoRests()).isEqualTo(16);
        assertThat(legislationProfile.getWorkingRestDuration()).isEqualTo(17);
        assertThat(legislationProfile.getMaxWaitingTime()).isEqualTo(18);
        assertThat(legislationProfile.isWaitingIsWorking()).isFalse();

        assertThat(legislationProfile.getDrivingPauseDurationCuts().size()).isEqualTo(3);
        assertThat(legislationProfile.getDrivingPauseDurationCuts().get(0)).isEqualTo(5);
        assertThat(legislationProfile.getDrivingPauseDurationCuts().get(1)).isEqualTo(10);
        assertThat(legislationProfile.getDrivingPauseDurationCuts().get(2)).isEqualTo(15);

        assertThat(legislationProfile.getWorkingPauseDurationCuts().size()).isEqualTo(2);
        assertThat(legislationProfile.getWorkingPauseDurationCuts().get(0)).isEqualTo(6);
        assertThat(legislationProfile.getWorkingPauseDurationCuts().get(1)).isEqualTo(11);

        assertThat(legislationProfile.getDrivingRestDurationCuts().size()).isEqualTo(4);
        assertThat(legislationProfile.getDrivingRestDurationCuts().get(0)).isEqualTo(7);
        assertThat(legislationProfile.getDrivingRestDurationCuts().get(1)).isEqualTo(12);
        assertThat(legislationProfile.getDrivingRestDurationCuts().get(2)).isEqualTo(17);
        assertThat(legislationProfile.getDrivingRestDurationCuts().get(3)).isEqualTo(22);

        assertThat(legislationProfile.getWorkingPauseDurationCuts().size()).isEqualTo(2);
        assertThat(legislationProfile.getWorkingRestDurationCuts().get(0)).isEqualTo(8);
    }
}
