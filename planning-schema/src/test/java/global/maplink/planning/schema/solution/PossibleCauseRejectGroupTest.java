package global.maplink.planning.schema.solution;

import global.maplink.json.JsonMapper;
import org.junit.jupiter.api.Test;

import static global.maplink.planning.testUtils.SampleFiles.POSSIBLE_CAUSE_REJECT_GROUP;
import static org.assertj.core.api.Assertions.assertThat;


class PossibleCauseRejectGroupTest {

    private final JsonMapper mapper = JsonMapper.loadDefault();

    @Test
    void shouldDeserialize(){

        PossibleCauseRejectGroup possibleCauseRejectGroup = mapper.fromJson(POSSIBLE_CAUSE_REJECT_GROUP.load(), PossibleCauseRejectGroup.class);

        assertThat(possibleCauseRejectGroup.getGroupId()).isEqualTo("exemplo1");
        assertThat(possibleCauseRejectGroup.getPossibleCauseRejects().get(0).getCode()).isEqualTo("exemplo1");
        assertThat(possibleCauseRejectGroup.getPossibleCauseRejects().get(0).getMessage()).isEqualTo("exemplo2");
    }
}