package global.maplink.geocode.schema.v1.suggestions;

import global.maplink.geocode.schema.v2.suggestions.SuggestionsResult;
import global.maplink.json.JsonMapper;
import org.junit.jupiter.api.Test;

import static global.maplink.geocode.schema.v2.Type.ROAD;
import static global.maplink.geocode.testUtils.SampleFiles.SUGGESTIONS_RESPONSE_V1;
import static java.util.Collections.emptyList;
import static org.assertj.core.api.Assertions.assertThat;

public class SuggestionsResultTest {
    public static final String FIRST_ID = "b77c04eb-92b1-4383-aaf8-d1f40ff50f9a";
    private final JsonMapper mapper = JsonMapper.loadDefault();

    @Test
    public void mustBeCreatedByJson() {

        SuggestionsResult response = mapper.fromJson(SUGGESTIONS_RESPONSE_V1.load(), SuggestionsResult.class);

        assertThat(response.getFound()).isEqualTo(317);
        assertThat(response.getResults()).hasSize(10);
        assertThat(response.getMostRelevant().getType()).isEqualTo(ROAD);
        assertThat(response.getMostRelevant().getId()).isEqualTo(FIRST_ID);
        assertThat(response).isNotEmpty().hasSize(10);
        assertThat(response.stream()).isNotEmpty().hasSize(10);
        assertThat(response.parallelStream()).isNotEmpty().hasSize(10);
    }

    @Test
    public void mustReturnNullOnEmptyResultsForMostRelevant() {
        SuggestionsResult emptyResult = new SuggestionsResult(0, emptyList());
        assertThat(emptyResult.getMostRelevant()).isNull();
        assertThat(emptyResult.getResults()).isEmpty();
        assertThat(emptyResult.getFound()).isZero();
        assertThat(emptyResult).isEmpty();
        assertThat(emptyResult.stream()).isEmpty();
        assertThat(emptyResult.parallelStream()).isEmpty();
    }

    @Test
    public void mustReturnNullOnNullResultsForMostRelevant() {
        SuggestionsResult emptyResult = new SuggestionsResult(0, null);
        assertThat(emptyResult.getMostRelevant()).isNull();
        assertThat(emptyResult.getResults()).isNull();
        assertThat(emptyResult.getFound()).isZero();
        assertThat(emptyResult).isEmpty();
        assertThat(emptyResult.stream()).isEmpty();
        assertThat(emptyResult.parallelStream()).isEmpty();
    }
}
