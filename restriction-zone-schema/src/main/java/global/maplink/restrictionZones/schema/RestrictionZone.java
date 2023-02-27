package global.maplink.restrictionZones.schema;

import global.maplink.domain.MaplinkPolygon;
import lombok.*;

import java.util.List;

@Data
@RequiredArgsConstructor
@NoArgsConstructor(force = true)
@Builder
public class RestrictionZone {
    private final String name;
    private final String roadMap;
    @Singular
    private final List<MaplinkPolygon> geometries;
}
