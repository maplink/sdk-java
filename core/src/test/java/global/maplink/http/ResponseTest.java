package global.maplink.http;

import global.maplink.json.JsonMapper;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.junit.jupiter.api.Test;

import java.util.List;

import static java.nio.charset.StandardCharsets.UTF_8;
import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;
import static java.util.stream.IntStream.range;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ResponseTest {
    private static final String APPLICATION_JSON = "application/json";

    private static final String SAMPLE_NAME_VALUE = "test";
    private static final String SAMPLE_JSON = "{'name':'" + SAMPLE_NAME_VALUE + "'}";

    private static final SampleJson SAMPLE_OBJECT = new SampleJson(SAMPLE_NAME_VALUE);


    @Test
    public void mustEvaluate2xxAsOkStatus() {
        assertOk(buildWithStatus(200));
        assertOk(buildWithStatus(201));
        assertOk(buildWithStatus(204));
    }

    @Test
    public void mustEvaluate4xxAsClientError() {
        assertClientError(buildWithStatus(400));
        assertClientError(buildWithStatus(403));
        assertClientError(buildWithStatus(404));
    }

    @Test
    public void mustEvaluate5xxAsServerError() {
        assertServerError(buildWithStatus(500));
        assertServerError(buildWithStatus(503));
        assertServerError(buildWithStatus(504));
    }


    @Test
    public void mustEvaluateOthersAsServerError() {
        Response tooHigh = buildWithStatus(650);
        assertThat(tooHigh.isError()).isTrue();
        assertThat(tooHigh.isServerError()).isFalse();
        assertThat(tooHigh.isClientError()).isFalse();
        assertThat(tooHigh.isOk()).isFalse();


        Response tooLow = buildWithStatus(50);
        assertThat(tooLow.isError()).isTrue();
        assertThat(tooLow.isServerError()).isFalse();
        assertThat(tooLow.isClientError()).isFalse();
        assertThat(tooLow.isOk()).isFalse();

        assertThat(buildWithStatus(0).isError()).isTrue();
        assertThat(buildWithStatus(-200).isError()).isTrue();
    }

    @Test
    public void mustParseBodyAsJsonObject() {
        JsonMapper mapper = mock(JsonMapper.class);
        byte[] body = SAMPLE_JSON.getBytes(UTF_8);
        when(mapper.fromJson(body, SampleJson.class)).thenReturn(SAMPLE_OBJECT);
        Response response = new Response(200, APPLICATION_JSON, body);
        SampleJson parsed = response.parseBodyObject(mapper, SampleJson.class);
        assertThat(parsed).isEqualTo(SAMPLE_OBJECT);
    }

    @Test
    public void mustParseBodyAsJsonArray() {
        int size = 10;
        JsonMapper mapper = mock(JsonMapper.class);
        List<SampleJson> sampleObjects = range(0, size)
                .mapToObj(i -> SAMPLE_OBJECT)
                .collect(toList());
        byte[] body = ("[" +
                range(0, size)
                        .mapToObj(i -> SAMPLE_JSON)
                        .collect(joining(","))
                + "]").getBytes(UTF_8);

        when(mapper.fromJsonList(body, SampleJson.class)).thenReturn(sampleObjects);
        Response response = new Response(200, APPLICATION_JSON, body);
        List<SampleJson> parsed = response.parseBodyArray(mapper, SampleJson.class);
        assertThat(parsed).isEqualTo(sampleObjects);
    }

    private void assertOk(Response resp) {
        assertThat(resp.isOk()).isTrue();
        assertThat(resp.isClientError()).isFalse();
        assertThat(resp.isError()).isFalse();
        assertThat(resp.isServerError()).isFalse();
    }

    private void assertClientError(Response resp) {
        assertThat(resp.isOk()).isFalse();
        assertThat(resp.isClientError()).isTrue();
        assertThat(resp.isError()).isTrue();
        assertThat(resp.isServerError()).isFalse();
    }

    private void assertServerError(Response resp) {
        assertThat(resp.isOk()).isFalse();
        assertThat(resp.isClientError()).isFalse();
        assertThat(resp.isError()).isTrue();
        assertThat(resp.isServerError()).isTrue();
    }


    private Response buildWithStatus(int status) {
        return new Response(status, "none", new byte[0]);
    }

    @AllArgsConstructor
    @Data
    private static class SampleJson {
        private final String name;
    }
}
