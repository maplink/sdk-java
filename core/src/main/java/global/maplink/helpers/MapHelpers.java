package global.maplink.helpers;

import lombok.NoArgsConstructor;
import lombok.val;

import java.util.HashMap;
import java.util.Map;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public class MapHelpers {

    public static Map<String, String> mapOf(String... values) {
        assert values.length % 2 == 0;
        val map = new HashMap<String, String>();
        for (int i = 0; i < values.length; i += 2) {
            map.put(values[i], values[i + 1]);
        }
        return map;
    }

}
