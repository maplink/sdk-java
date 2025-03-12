package global.maplink.geocode.schema;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor(force = true)
@AllArgsConstructor
public class State {

    private final String code;
    private final String name;
}
