package global.maplink.helpers;

import org.junit.jupiter.api.Test;

import java.util.AbstractMap.SimpleEntry;
import java.util.Map;

import static global.maplink.helpers.MapHelpers.mapOf;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class MapHelpersTest {
    @Test
    void mustCreateMapFromKeyValuePair() {
        Map<String, String> map = mapOf(
                "K1", "V1",
                "K2", "V2"
        );
        assertThat(map)
                .containsExactly(
                        new SimpleEntry<>("K1", "V1"),
                        new SimpleEntry<>("K2", "V2")
                );
    }

    @Test
    void mustFailWhenOddValueOfArgumentsAreProvided() {
        assertThatThrownBy(() -> mapOf("K1", "V1", "K2"))
                .isInstanceOf(IllegalArgumentException.class);
    }


    @Test
    void mustReturnEmptyMapOnEmptyArgs() {
        Map<String, String> map = mapOf();
        assertThat(map).isEmpty();
    }
}
