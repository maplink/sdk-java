package global.maplink.geocode.schema.v2.reverse;

import lombok.val;
import org.junit.jupiter.api.Test;

import static global.maplink.geocode.schema.v2.reverse.ReverseBaseRequest.entry;
import static java.util.stream.Collectors.toList;
import static java.util.stream.IntStream.range;
import static org.assertj.core.api.Assertions.assertThat;

class ReverseRequestTest {

    @Test
    void splitMustWorkForSub200List() {
        val request = generate(100);
        val split = request.split();
        assertThat(split).hasSize(1);
        assertThat(split.get(0).getEntries()).hasSameElementsAs(request.getEntries());
    }

    @Test
    void splitMustWorkForOver200List() {
        val request = generate(500);
        val split = request.split();
        assertThat(split).hasSize(3);
        assertThat(split.get(0).getEntries()).hasSameElementsAs(request.getEntries().subList(0, 200));
        assertThat(split.get(1).getEntries()).hasSameElementsAs(request.getEntries().subList(200, 400));
        assertThat(split.get(2).getEntries()).hasSameElementsAs(request.getEntries().subList(400, 500));
    }

    private ReverseBaseRequest generate(int entriesNumber) {
        val entries = range(0, entriesNumber)
                .mapToObj(i -> entry(
                        "id-" + i,
                        -22.9141308 + (i * 0.000001),
                        -43.445982 + (i * 0.000001)
                )).collect(toList());
        return ReverseBaseRequest.of(entries);
    }
}