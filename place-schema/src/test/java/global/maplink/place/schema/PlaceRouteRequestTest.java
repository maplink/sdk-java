package global.maplink.place.schema;

import global.maplink.json.JsonMapper;
import global.maplink.place.schema.exception.PlaceCalculationRequestException;
import global.maplink.validations.ValidationException;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Arrays;

import static global.maplink.place.schema.SubCategory.*;
import static global.maplink.place.testUtils.SampleFiles.PLACEROUTEREQUEST;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatCode;
import static org.junit.jupiter.api.Assertions.*;

public class PlaceRouteRequestTest {

    private final JsonMapper mapper = JsonMapper.loadDefault();

    @Test
    public void shouldDeserialize() {
        PlaceRouteRequest placeRouteRequest = mapper.fromJson(PLACEROUTEREQUEST.load(), PlaceRouteRequest.class);
        assertEquals(3, placeRouteRequest.getCategories().size());
        assertTrue(placeRouteRequest.getCategories().containsAll(Arrays.asList(Category.ADVOGADOS, Category.AGENCIAS, Category.BANCOS)));
        assertEquals(5, placeRouteRequest.getSubCategories().size());
        assertTrue(placeRouteRequest.getSubCategories().containsAll(Arrays.asList(ADVOGADOS, ADVOGADOS_CAUSAS_AMBIENTAIS,
                AGENCIAS_DE_EMPREGOS, BANCOS_DE_IMAGENS, BANCOS_DE_SANGUE)));
        assertEquals(50, placeRouteRequest.getBufferRouteInMeters());
        assertEquals(100, placeRouteRequest.getBufferStoppingPointsInMeters());
        assertFalse(placeRouteRequest.isOnlyMyPlaces());

        assertEquals(1, placeRouteRequest.getLegs().size());
        assertEquals(3, placeRouteRequest.getLegs().get(0).getPoints().size());

        Leg leg = placeRouteRequest.getLegs().get(0);
        assertEquals(0, new BigDecimal("-23.5666499").compareTo(leg.getPoints().get(0).getLatitude()));
        assertEquals(0, new BigDecimal("-46.6557755").compareTo(leg.getPoints().get(0).getLongitude()));
        assertEquals(0, new BigDecimal("-23.5688742").compareTo(leg.getPoints().get(1).getLatitude()));
        assertEquals(0, new BigDecimal("-46.6638823").compareTo(leg.getPoints().get(1).getLongitude()));
        assertEquals(0, new BigDecimal("-23.5757982").compareTo(leg.getPoints().get(2).getLatitude()));
        assertEquals(0, new BigDecimal("-46.6779297").compareTo(leg.getPoints().get(2).getLongitude()));
        assertThat(placeRouteRequest.validate()).isEmpty();
        assertThatCode(placeRouteRequest::throwIfInvalid).doesNotThrowAnyException();
        assertThatCode(placeRouteRequest::validateWithLegs).doesNotThrowAnyException();
    }

    @Test
    void shouldValidateEmptyRequest() {
        PlaceRouteRequest emptyRequest = PlaceRouteRequest.builder().build();
        assertThat(emptyRequest.validate()).isNotEmpty().hasSize(3);
        assertThatThrownBy(emptyRequest::throwIfInvalid).isInstanceOf(ValidationException.class);
        assertThatThrownBy(emptyRequest::validateWithLegs).isInstanceOf(ValidationException.class);
    }

    @Test
    void shouldValidateTooBigRequest() {
        PlaceRouteRequest tooBiRequest = PlaceRouteRequest.builder()
                .bufferStoppingPointsInMeters(Long.MAX_VALUE)
                .bufferRouteInMeters(Long.MAX_VALUE)
                .category(Category.POSTOS_DE_COMBUSTIVEL)
                .subCategory(POSTOS_DE_COMBUSTIVEL)
                .build();
        assertThat(tooBiRequest.validate()).isNotEmpty().hasSize(2);
        assertThatThrownBy(tooBiRequest::throwIfInvalid).isInstanceOf(ValidationException.class);
        assertThatThrownBy(tooBiRequest::validateWithLegs).isInstanceOf(ValidationException.class);
    }

    @Test
    void shouldValidateWithLegs() {
        PlaceRouteRequest emptyLegsRequest = PlaceRouteRequest.builder()
                .bufferStoppingPointsInMeters(10L)
                .bufferRouteInMeters(10L)
                .category(Category.POSTOS_DE_COMBUSTIVEL)
                .subCategory(POSTOS_DE_COMBUSTIVEL)
                .build();
        assertThat(emptyLegsRequest.validate()).isEmpty();
        assertThatThrownBy(emptyLegsRequest::validateWithLegs).isInstanceOf(PlaceCalculationRequestException.class);
    }
}
