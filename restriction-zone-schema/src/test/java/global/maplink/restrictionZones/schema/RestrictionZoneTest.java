package global.maplink.restrictionZones.schema;

import global.maplink.domain.MaplinkPolygon;
import global.maplink.json.JsonMapper;
import org.junit.jupiter.api.Test;

import java.util.List;

import static global.maplink.restrictionZones.schema.SampleFiles.RESTRICTION_ZONE;
import static global.maplink.restrictionZones.schema.SampleFiles.RESTRICTION_ZONE_LIST;
import static org.assertj.core.api.Assertions.assertThat;

class RestrictionZoneTest {

    private final JsonMapper mapper = JsonMapper.loadDefault();

    @Test
    void shouldDeserializeFromJson() {
        RestrictionZone rz = mapper.fromJson(RESTRICTION_ZONE.load(), RestrictionZone.class);
        assertThat(rz.getName()).isEqualTo("Sample1");
        assertThat(rz.getRoadMap()).isEqualTo("MAPLINKBR_202201");
        assertThat(rz.getGeometries()).isNotEmpty().hasSize(1);

        MaplinkPolygon firstGeometry = rz.getGeometries().get(0);
        assertThat(firstGeometry.getName()).isEqualTo("Polygon1-1");
        assertThat(firstGeometry.getVertices()).isNotEmpty().hasSize(7);
    }

    @Test
    void shouldDeserializeFromJsonList() {
        List<RestrictionZone> rzs = mapper.fromJsonList(RESTRICTION_ZONE_LIST.load(), RestrictionZone.class);
        assertThat(rzs).hasSize(2);
        RestrictionZone first = rzs.get(0);
        assertThat(first.getName()).isEqualTo("Sample1");
        assertThat(first.getRoadMap()).isEqualTo("MAPLINKBR_202201");
        assertThat(first.getGeometries()).isNotEmpty().hasSize(1);

        MaplinkPolygon firstGeometry = first.getGeometries().get(0);
        assertThat(firstGeometry.getName()).isEqualTo("Polygon1-1");
        assertThat(firstGeometry.getVertices()).isNotEmpty().hasSize(7);
    }

}