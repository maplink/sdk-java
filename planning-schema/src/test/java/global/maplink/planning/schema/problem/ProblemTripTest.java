package global.maplink.planning.schema.problem;

import global.maplink.json.JsonMapper;

import org.junit.jupiter.api.Test;

import static global.maplink.planning.testUtils.ProblemSampleFiles.PROBLEM_TRIP;
import static org.assertj.core.api.Assertions.assertThat;
import static global.maplink.trip.schema.v2.problem.CalculationMode.THE_FASTEST;

class ProblemTripTest {

    private final JsonMapper mapper = JsonMapper.loadDefault();

    @Test
    void shouldDeserialize(){

        ProblemTrip problemTrip = mapper.fromJson(PROBLEM_TRIP.load(), ProblemTrip.class);

        assertThat(problemTrip.getProfileName()).isEqualTo("name1");
        assertThat(problemTrip.getCalculationMode()).isEqualTo(THE_FASTEST);
        assertThat(problemTrip.getStartDate()).isEqualTo(10L);

    }
}
