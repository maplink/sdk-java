package global.maplink.place.schema;

import global.maplink.json.JsonMapper;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.stream.Collectors;

import static global.maplink.place.schema.Category.*;
import static global.maplink.place.schema.SubCategory.*;
import static global.maplink.place.schema.SubCategory.ADVOGADOS;
import static global.maplink.place.testUtils.SampleFiles.ADDRESS;
import static global.maplink.place.testUtils.SampleFiles.PLACEROUTEREQUEST;
import static org.junit.jupiter.api.Assertions.*;

public class PlaceRouteRequestTest {

    private final JsonMapper mapper = JsonMapper.loadDefault();

    @Test
    public void shouldSerialize(){
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
    }
}
