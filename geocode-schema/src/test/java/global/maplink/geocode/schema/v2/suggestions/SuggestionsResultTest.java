package global.maplink.geocode.schema.v2.suggestions;

import global.maplink.geocode.schema.v1.suggestions.SuggestionsResult;
import global.maplink.json.JsonMapper;
import org.junit.jupiter.api.Test;

import static global.maplink.geocode.schema.v1.Type.ROAD;
import static global.maplink.geocode.testUtils.SampleFiles.SUGGESTIONS_RESPONSE_V2;
import static org.assertj.core.api.Assertions.assertThat;

public class SuggestionsResultTest {
    public static final String FIRST_ID = "6684353bf0662a1541ff635d";
    private final JsonMapper mapper = JsonMapper.loadDefault();

    @Test
    public void mustBeCreatedByJson() {
        SuggestionsResult response = mapper.fromJson(SUGGESTIONS_RESPONSE_V2.load(), SuggestionsResult.class);
        assertThat(response.getFound()).isEqualTo(10);
        assertThat(response.getResults()).hasSize(10);
        assertThat(response.getMostRelevant().getType()).isEqualTo(ROAD);
        assertThat(response.getMostRelevant().getId()).isEqualTo(FIRST_ID);
        assertThat(response).isNotEmpty().hasSize(10);
        assertThat(response.stream()).isNotEmpty().hasSize(10);
        assertThat(response.parallelStream()).isNotEmpty().hasSize(10);
        assertThat(response.getResults().get(0).getAddress().getNumberAsInteger()).isEqualTo(12);
    }

}
