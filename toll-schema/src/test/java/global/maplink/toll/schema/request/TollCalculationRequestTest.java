package global.maplink.toll.schema.request;

import global.maplink.json.JsonMapper;
import global.maplink.toll.schema.Coordinates;
import global.maplink.toll.schema.TollConditionPeriod;
import lombok.val;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.Collections;
import java.util.HashSet;

import static global.maplink.commons.TransponderOperator.CONECTCAR;
import static global.maplink.commons.TransponderOperator.SEM_PARAR;
import static global.maplink.toll.schema.Billing.FREE_FLOW;
import static global.maplink.toll.schema.TollConditionBillingType.TAG;
import static global.maplink.toll.schema.TollConditionPeriod.HOLIDAY;
import static global.maplink.toll.schema.TollVehicleType.CAR;
import static global.maplink.toll.testUtils.SampleFiles.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.offset;

class TollCalculationRequestTest {

    private final JsonMapper mapper = JsonMapper.loadDefault();

    @Test
    void shouldDeserializeWithDefaultTransponderOperator() {
        val data = mapper.fromJson(CALCULATION_REQUEST.load(), TollCalculationRequest.class);
        assertThat(data.getBilling()).isEqualTo(FREE_FLOW);
        assertThat(data.getLegs())
                .hasSize(1);
        assertThat(data.getTransponderOperators()).isEqualTo(new HashSet<>(Collections.singletonList(SEM_PARAR)));
    }

    @Test
    void shouldDeserialize() {
        val data = mapper.fromJson(CALCULATION_REQUEST_CONECTCAR.load(), TollCalculationRequest.class);
        assertThat(data.getBilling()).isEqualTo(FREE_FLOW);

        assertThat(data.getLegs())
                .hasSize(1);

        assertThat(data.getTransponderOperators()).isEqualTo(new HashSet<>(Collections.singletonList(CONECTCAR)));

        val firstLeg = data.getLegs().get(0);
        assertThat(firstLeg.getVehicleType()).isEqualTo(CAR);
        assertThat(firstLeg.getPoints()).hasSize(18);

        val firstPoint = firstLeg.getPoints().get(0);
        assertPoint(firstPoint, -22.0539, -42.36768);

        val lastPoint = firstLeg.getPoints().get(firstLeg.getPoints().size() - 1);
        assertPoint(lastPoint, -22.05251, -42.35759);
    }

    @Test
    void shouldDeserializeRequestWithSpecificConditions() {
        val data = mapper.fromJson(CALCULATION_REQUEST_CONDITIONS.load(), TollCalculationRequest.class);

        LegRequest firstLeg = data.getLegs().stream().findFirst().orElseThrow(IllegalArgumentException::new);
        assertThat(firstLeg.getCalculationDate()).isEqualTo(Instant.ofEpochMilli(1710769071000L));
        assertThat(firstLeg.getCondition().getBillingType()).isEqualTo(TAG);
        assertThat(firstLeg.getCondition().getPeriod()).isEqualTo(HOLIDAY);
        assertThat(data.getLegs())
                .hasSize(1);
        assertThat(data.getTransponderOperators()).containsExactly(SEM_PARAR);
    }

    @Test
    void shouldValidateDefaultCalculationDateAndRequestProperties() {
        val data = mapper.fromJson(CALCULATION_DATE_DEFAULT.load(), TollCalculationRequest.class);

        LegRequest firstLeg = data.getLegs().stream().findFirst().orElseThrow(IllegalArgumentException::new);
        assertThat(firstLeg.getCalculationDate()).isNotNull();
        assertThat(firstLeg.getCondition().getBillingType()).isEqualTo(TAG);
        assertThat(firstLeg.getCondition().getPeriod()).isEqualTo(HOLIDAY);
        assertThat(data.getLegs())
                .hasSize(1);
        assertThat(data.getTransponderOperators()).containsExactly(SEM_PARAR);
    }

    @Test
    void shouldDefaultCalculationCondition() {
        val data = mapper.fromJson(CALCULATION_CONDITIONS_DEFAULT.load(), TollCalculationRequest.class);

        LegRequest firstLeg = data.getLegs().stream().findFirst().orElseThrow(IllegalArgumentException::new);
        assertThat(firstLeg.getCalculationDate()).isEqualTo(Instant.ofEpochMilli(1710769071000L));
        assertThat(firstLeg.getCondition().getBillingType()).isEqualTo(TAG);
        assertThat(firstLeg.getCondition().getPeriod()).isEqualTo(TollConditionPeriod.NORMAL);
        assertThat(data.getLegs())
                .hasSize(1);
        assertThat(data.getTransponderOperators()).containsExactly(SEM_PARAR);
    }

    @Test
    void shouldDefaultCalculation() {
        val data = mapper.fromJson(CALCULATION_DEFAULT.load(), TollCalculationRequest.class);

        LegRequest firstLeg = data.getLegs().stream().findFirst().orElseThrow(IllegalArgumentException::new);
        assertThat(firstLeg.getCalculationDate()).isNotNull();
        assertThat(data.getLegs())
                .hasSize(1);
        assertThat(data.getTransponderOperators()).containsExactly(SEM_PARAR);
    }

    @Test
    void shouldSerializeWithInactiveTrue() {
        val request = TollCalculationRequest.builder()
                .leg(LegRequest.builder()
                        .vehicleType(CAR)
                        .point(Coordinates.of(-22.0539, -42.36768))
                        .build())
                .inactive(true)
                .build();

        val json = new String(mapper.toJson(request));
        assertThat(json).contains("\"inactive\":true");
    }

    @Test
    void shouldDefaultInactiveToFalse() {
        val request = TollCalculationRequest.builder()
                .leg(LegRequest.builder()
                        .vehicleType(CAR)
                        .point(Coordinates.of(-22.0539, -42.36768))
                        .build())
                .build();

        assertThat(request.isInactive()).isFalse();
    }

    private void assertPoint(Coordinates point, double lat, double lon) {
        assertThat(point.getLatitude().doubleValue()).isCloseTo(lat, offset(0.0001));
        assertThat(point.getLongitude().doubleValue()).isCloseTo(lon, offset(0.0001));
    }

}