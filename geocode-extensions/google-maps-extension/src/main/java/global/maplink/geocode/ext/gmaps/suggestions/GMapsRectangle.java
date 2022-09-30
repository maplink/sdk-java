package global.maplink.geocode.ext.gmaps.suggestions;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@NoArgsConstructor(force = true)
@Data
public class GMapsRectangle {
    private final GMapsPoint northeast;
    private final GMapsPoint southwest;
}
