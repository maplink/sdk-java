package global.maplink.geocode.schema.structured;

import lombok.val;
import org.junit.jupiter.api.Test;

import java.util.List;

import static java.util.Arrays.asList;
import static java.util.stream.Collectors.toList;
import static java.util.stream.IntStream.range;
import static org.assertj.core.api.Assertions.assertThat;

class StructuredRequestTest {

    @Test
    void splitMustWorkForSingle() {
        val request = StructuredRequest.ofState("teste", "teste");
        val split = request.split();
        assertThat(split).hasSize(1);
        assertThat(split.get(0)).isEqualTo(request);
    }

    @Test
    void splitMustWorkForSub200List() {
        val request = generate(100);
        val split = request.split();
        assertThat(split).hasSize(1);
        assertThat(split.get(0).getRequests()).containsExactly(request.getRequests());
    }

    @Test
    void splitMustWorkForOver200List() {
        val request = generate(500);
        val split = request.split();
        assertThat(split).hasSize(3);
        List<StructuredRequest.Single> list = asList(request.getRequests());
        assertThat(split.get(0).getRequests()).hasSameElementsAs(list.subList(0, 200));
        assertThat(split.get(1).getRequests()).hasSameElementsAs(list.subList(200, 400));
        assertThat(split.get(2).getRequests()).hasSameElementsAs(list.subList(400, 500));
    }

    private StructuredRequest.Multi generate(int entriesNumber) {
        List<StructuredRequest.Single> entries = range(0, entriesNumber)
                .mapToObj(i -> StructuredRequest.ofState("id-" + i, "Sao paulo " + i))
                .collect(toList());
        return StructuredRequest.multi(entries);
    }
}