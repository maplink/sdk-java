package global.maplink.geocode.schema.v2;

import global.maplink.domain.MaplinkPoint;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor(force = true)
@AllArgsConstructor
public class MainLocation {

    private Integer radius;
    private MaplinkPoint center;
}
