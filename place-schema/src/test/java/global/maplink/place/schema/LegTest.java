package global.maplink.place.schema;

import global.maplink.json.JsonMapper;
import org.junit.jupiter.api.Test;

import static global.maplink.place.testUtils.Defaults.POINT_OFFSET;
import static global.maplink.place.testUtils.SampleFiles.LEG;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class LegTest {

    private final JsonMapper mapper = JsonMapper.loadDefault();

    @Test
    public void shouldDeserialize(){
        Leg leg = mapper.fromJson(LEG.load(), Leg.class);
        assertEquals(3, leg.getPoints().size());

        int i = 0;

        assertThat( leg.getPoints().get(i).getLatitude()).isCloseTo(-23.5666499, POINT_OFFSET);
        assertThat( leg.getPoints().get(i++).getLongitude()).isCloseTo(-46.6557755, POINT_OFFSET);

        assertThat( leg.getPoints().get(i).getLatitude()).isCloseTo(-23.5688742, POINT_OFFSET);
        assertThat( leg.getPoints().get(i++).getLongitude()).isCloseTo(-46.6638823, POINT_OFFSET);

        assertThat( leg.getPoints().get(i).getLatitude()).isCloseTo(-23.5757982, POINT_OFFSET);
        assertThat( leg.getPoints().get(i).getLongitude()).isCloseTo(-46.6779297, POINT_OFFSET);
    }
}
