package global.maplink.geocode.ext.gmaps.suggestions;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor(force = true)
@AllArgsConstructor
@Data
public class GMapsPoint {
    private final double lat;
    private final double lng;
}
