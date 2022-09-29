package global.maplink.geocode.ext.gmaps.suggestions;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.util.Set;

@RequiredArgsConstructor
@NoArgsConstructor(force = true)
@Data
public class GMapsAddressComponent {
    private final String long_name;
    private final String short_name;
    private final Set<String> types;
}
