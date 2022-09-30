package global.maplink.geocode.ext.gmaps.suggestions;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@NoArgsConstructor(force = true)
@Data
public class GMapsGeometry {

    private final GMapsRectangle bounds;

    private final GMapsPoint location;

    private final String location_type;

    private final GMapsRectangle viewport;
}
