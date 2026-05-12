package global.maplink.place.schema;

import global.maplink.json.JsonMapper;
import global.maplink.place.schema.exception.PlaceCalculationRequestException;
import global.maplink.validations.ValidationException;
import global.maplink.validations.ValidationViolation;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import static global.maplink.place.schema.SubCategory.*;
import static global.maplink.place.testUtils.Defaults.POINT_OFFSET;
import static global.maplink.place.testUtils.SampleFiles.PLACEROUTEREQUEST;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatCode;
import static org.junit.jupiter.api.Assertions.*;

public class PlaceRouteRequestTest {

    private static final long ABOVE_MAX_BUFFER = PlaceRouteRequest.MAX_BUFFER + 1L;

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
        assertEquals(3, leg.getPoints().size());
        int i = 0;
        assertThat(leg.getPoints().get(i).getLatitude()).isCloseTo(-23.5666499, POINT_OFFSET);
        assertThat(leg.getPoints().get(i++).getLongitude()).isCloseTo(-46.6557755, POINT_OFFSET);

        assertThat(leg.getPoints().get(i).getLatitude()).isCloseTo(-23.5688742, POINT_OFFSET);
        assertThat(leg.getPoints().get(i++).getLongitude()).isCloseTo(-46.6638823, POINT_OFFSET);

        assertThat(leg.getPoints().get(i).getLatitude()).isCloseTo(-23.5757982, POINT_OFFSET);
        assertThat(leg.getPoints().get(i).getLongitude()).isCloseTo(-46.6779297, POINT_OFFSET);

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

    @ParameterizedTest
    @MethodSource("invalidRequestProvider")
    void shouldRejectInvalidRequest(PlaceRouteRequest request, String expectedMessage) {
        List<ValidationViolation> violations = request.validate();

        assertThatThrownBy(request::throwIfInvalid).isInstanceOf(ValidationException.class);
        assertThat(violations).hasSize(1);
        assertThat(violations.get(0).getMessage()).isEqualTo(expectedMessage);
    }

    @ParameterizedTest
    @MethodSource("validRequestProvider")
    void shouldAcceptValidRequest(PlaceRouteRequest request) {
        assertThat(request.validate()).isEmpty();
    }

    static Stream<Arguments> invalidRequestProvider() {
        return Stream.of(
                Arguments.of(validRequest().bufferRouteInMeters(null).build(),                 "The route buffer should be bigger than zero"),
                Arguments.of(validRequest().bufferRouteInMeters(0L).build(),                   "The route buffer should be bigger than zero"),
                Arguments.of(validRequest().bufferRouteInMeters(-1L).build(),                  "The route buffer should be bigger than zero"),
                Arguments.of(validRequest().bufferRouteInMeters(ABOVE_MAX_BUFFER).build(),     "The route buffer should be less than 500"),
                Arguments.of(validRequest().bufferStoppingPointsInMeters(null).build(),        "The stopping points buffer should be bigger than zero"),
                Arguments.of(validRequest().bufferStoppingPointsInMeters(0L).build(),          "The stopping points buffer should be bigger than zero"),
                Arguments.of(validRequest().bufferStoppingPointsInMeters(-1L).build(),         "The stopping points buffer should be bigger than zero"),
                Arguments.of(validRequest().bufferStoppingPointsInMeters(ABOVE_MAX_BUFFER).build(), "The stopping points buffer should be less than 500"),
                Arguments.of(noCategoriesRequest().build(),                                    "Category or subcategory info is necessary")
        );
    }

    static Stream<Arguments> validRequestProvider() {
        return Stream.of(
                Arguments.of(validRequest().build()),
                Arguments.of(onlyCategoryRequest().build()),
                Arguments.of(onlySubCategoryRequest().build()),
                Arguments.of(validRequest().bufferRouteInMeters((long) PlaceRouteRequest.MAX_BUFFER).build()),
                Arguments.of(validRequest().bufferStoppingPointsInMeters((long) PlaceRouteRequest.MAX_BUFFER).build())
        );
    }

    private static PlaceRouteRequest.PlaceRouteRequestBuilder validRequest() {
        return PlaceRouteRequest.builder()
                .bufferRouteInMeters(10L)
                .bufferStoppingPointsInMeters(10L)
                .category(Category.POSTOS_DE_COMBUSTIVEL)
                .subCategory(POSTOS_DE_COMBUSTIVEL);
    }

    private static PlaceRouteRequest.PlaceRouteRequestBuilder onlyCategoryRequest() {
        return PlaceRouteRequest.builder()
                .bufferRouteInMeters(10L)
                .bufferStoppingPointsInMeters(10L)
                .category(Category.POSTOS_DE_COMBUSTIVEL);
    }

    private static PlaceRouteRequest.PlaceRouteRequestBuilder onlySubCategoryRequest() {
        return PlaceRouteRequest.builder()
                .bufferRouteInMeters(10L)
                .bufferStoppingPointsInMeters(10L)
                .subCategory(POSTOS_DE_COMBUSTIVEL);
    }

    private static PlaceRouteRequest.PlaceRouteRequestBuilder noCategoriesRequest() {
        return PlaceRouteRequest.builder()
                .bufferRouteInMeters(10L)
                .bufferStoppingPointsInMeters(10L);
    }
}
