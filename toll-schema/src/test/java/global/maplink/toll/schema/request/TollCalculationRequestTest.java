package global.maplink.toll.schema.request;

import global.maplink.json.JsonMapper;
import global.maplink.toll.schema.Coordinates;
import lombok.val;
import org.junit.jupiter.api.Test;

import static global.maplink.toll.schema.Billing.FREE_FLOW;
import static global.maplink.toll.schema.TollVehicleType.CAR;
import static global.maplink.toll.testUtils.SampleFiles.CALCULATION_REQUEST;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.offset;

class TollCalculationRequestTest {

    private final JsonMapper mapper = JsonMapper.loadDefault();

    @Test
    void shouldDeserialize() {
        val data = mapper.fromJson(CALCULATION_REQUEST.load(), TollCalculationRequest.class);
        assertThat(data.getBilling()).isEqualTo(FREE_FLOW);

        assertThat(data.getLegs())
                .hasSize(1);

        val firstLeg = data.getLegs().get(0);
        assertThat(firstLeg.getVehicleType()).isEqualTo(CAR);
        assertThat(firstLeg.getPoints()).hasSize(18);

        val firstPoint = firstLeg.getPoints().get(0);
        assertPoint(firstPoint, -22.0539, -42.36768);

        val lastPoint = firstLeg.getPoints().get(firstLeg.getPoints().size() - 1);
        assertPoint(lastPoint, -22.05251, -42.35759);
    }

    private void assertPoint(Coordinates point, double lat, double lon) {
        assertThat(point.getLatitude().doubleValue()).isCloseTo(lat, offset(0.0001));
        assertThat(point.getLongitude().doubleValue()).isCloseTo(lon, offset(0.0001));
    }

}