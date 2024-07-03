package global.maplink.planning.schema.solution;

import global.maplink.json.JsonMapper;
import org.junit.jupiter.api.Test;

import static global.maplink.planning.testUtils.SampleFiles.POSSIBLE_CAUSE_REJECT;
import static org.assertj.core.api.Assertions.assertThat;


class PossibleCauseRejectTest {

    private final JsonMapper mapper = JsonMapper.loadDefault();

    @Test
    void shouldDeserialize(){

        PossibleCauseReject possibleCauseReject = mapper.fromJson(POSSIBLE_CAUSE_REJECT.load(), PossibleCauseReject.class);

        assertThat(possibleCauseReject.getCode()).isEqualTo("exemplo1");
        assertThat(possibleCauseReject.getMessage()).isEqualTo("exemplo2");
    }
}