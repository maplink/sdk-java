package global.maplink.place.async;

import global.maplink.MapLinkSDK;
import global.maplink.credentials.InvalidCredentialsException;
import global.maplink.credentials.MapLinkCredentials;
import global.maplink.http.exceptions.MapLinkHttpException;
import global.maplink.place.schema.*;
import global.maplink.place.utils.TestPlaceUtils;
import lombok.SneakyThrows;
import lombok.val;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import java.util.List;
import java.util.concurrent.ExecutionException;

import static global.maplink.env.EnvironmentCatalog.HOMOLOG;
import static global.maplink.place.common.Defaults.DEFAULT_CLIENT_ID;
import static global.maplink.place.common.Defaults.DEFAULT_SECRET;
import static global.maplink.place.schema.Category.POSTOS_DE_COMBUSTIVEL;
import static global.maplink.place.utils.EnvCredentialsHelper.withEnvCredentials;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class PlaceAsyncApiTest {
    TestPlaceUtils testPlaceUtils = new TestPlaceUtils();

    @Order(1)
    @Test
    void shouldCreatePlacesInDatabase() {
        Place placeSP1 = testPlaceUtils.testPlaceCreator("Posto de teste 1", "1a2b3c", "SP", "Santos", "José Menino");
        Place placeSP2 = testPlaceUtils.testPlaceCreator("Posto de teste 2", "1a2b3d", "SP", "São Paulo", "Brooklin");
        Place placeRJ1 = testPlaceUtils.testPlaceCreator(
                "Posto de teste 3",
                "1a2b3e",
                "RJ",
                "Rio de Janeiro",
                "Copacabana"
        );

        withEnvCredentials(credentials -> {
            configureWith(credentials);
            val instance = PlaceAsyncAPI.getInstance();

            instance.create(placeSP1).get();
            instance.create(placeSP2).get();
            instance.create(placeRJ1).get();

            Place placeSp1Read = instance.getByOriginId("1a2b3c").get().get();
            Place placeSp2Read = instance.getByOriginId("1a2b3d").get().get();
            Place placeRj1Read = instance.getByOriginId("1a2b3e").get().get();

            assertThat(placeSp1Read.getName()).isEqualTo("Posto de teste 1");
            assertThat(placeSp2Read.getName()).isEqualTo("Posto de teste 2");
            assertThat(placeRj1Read.getName()).isEqualTo("Posto de teste 3");
        });
    }

    @Order(2)
    @Test
    void shouldListAllStates() {
        withEnvCredentials(credentials -> {
            configureWith(credentials);
            val instance = PlaceAsyncAPI.getInstance();

            ListAllStatesRequest request = ListAllStatesRequest.builder().build();
            List<String> statesResulted = instance.listAllStates(request).get();
            assertThat(statesResulted.toString()).isEqualTo(TestPlaceUtils.LIST_ALL_STATES_EXPECTED_RESULT);
        });
    }

    @Order(3)
    @SneakyThrows
    @Test
    void shouldListAllCitiesFormSP() {
        withEnvCredentials(credentials -> {
            configureWith(credentials);
            val instance = PlaceAsyncAPI.getInstance();

            ListAllCitiesRequest request = ListAllCitiesRequest.builder()
                    .state("SP")
                    .build();
            List<String> citiesResulted = instance.listAllCities(request).get();
            assertThat(citiesResulted.toString()).contains("Santos");
            assertThat(citiesResulted.toString()).contains("São Paulo");
        });
    }

    @Order(4)
    @Test
    void shouldListAllDistrictsFromSantosSp() {
        withEnvCredentials(credentials -> {
            configureWith(credentials);
            val instance = PlaceAsyncAPI.getInstance();

            ListAllDistrictsRequest request = ListAllDistrictsRequest.builder()
                    .state("SP")
                    .city("Santos")
                    .build();
            List<String> districtsResulted = instance.listAllDistricts(request).get();
            System.out.println("districtsResulted -->> " + districtsResulted.toString());
            assertThat(districtsResulted.toString()).isEqualTo(TestPlaceUtils.LIST_ALL_DISTRICTS_EXPECTED_RESULT);
        });
    }

    @Order(5)
    @Test
    void mustBeInstantiableWithGetInstance() {
        configureWith(MapLinkCredentials.ofKey(DEFAULT_CLIENT_ID, DEFAULT_SECRET));
        val instance = PlaceAsyncAPI.getInstance();
        assertThat(instance).isNotNull();
    }

    @Order(6)
    @Test
    void mustFailWithInvalidCredentials() {
        configureWith(MapLinkCredentials.ofKey(DEFAULT_CLIENT_ID, DEFAULT_SECRET));
        val instance = PlaceAsyncAPI.getInstance();
        assertThatThrownBy(() -> instance.calculate(validRequest()).get())
                .isInstanceOf(ExecutionException.class)
                .hasCauseInstanceOf(InvalidCredentialsException.class);
    }

    @Order(7)
    @Test
    void mustFailOnInvalidRequest() {
        withEnvCredentials(credentials -> {
            configureWith(credentials);
            val instance = PlaceAsyncAPI.getInstance(() -> "https://maplink.global");
            assertThatThrownBy(() -> instance.calculate(validRequest()).get())
                    .isInstanceOf(ExecutionException.class)
                    .hasCauseInstanceOf(MapLinkHttpException.class);
        });
    }

    @Order(8)
    @Test
    void mustResolveValidCalculationRequest() {
        withEnvCredentials(credentials -> {
            configureWith(credentials);
            val instance = PlaceAsyncAPI.getInstance();
            val result = instance.calculate(
                    validRequest()
            ).get();

            assertThat(result.getTotal()).isEqualTo(4);
            assertThat(result.getLegs()).isNotEmpty().hasSize(1);
            LegResult firstLeg = result.getLegs().get(0);
            assertThat(firstLeg.getTotal()).isEqualTo(4);
            assertThat(firstLeg.getPlaces()).hasSize(4);
            PlaceRoute firstPlace = firstLeg.getPlaces().get(0);
            assertThat(firstPlace.getId()).isNotNull().isNotEmpty();
            assertThat(firstPlace.getName()).isNotNull().isNotEmpty();
            assertThat(firstPlace.getCategory()).isEqualTo(POSTOS_DE_COMBUSTIVEL);
            assertThat(firstPlace.getSubCategory()).isEqualTo(SubCategory.POSTOS_DE_COMBUSTIVEL);
            assertThat(firstPlace.getAddress())
                    .isNotNull()
                    .extracting(Address::getZipcode).isNotNull();
            assertThat(firstPlace.getAddress().getPoint())
                    .isNotNull()
                    .isEqualTo(Point.of(-23.368653161261896, -46.77969932556152));
            assertThat(firstPlace.getPhones()).isNotEmpty();
        });
    }

    private static PlaceRouteRequest validRequest() {
        return PlaceRouteRequest.builder()
                .category(POSTOS_DE_COMBUSTIVEL)
                .bufferRouteInMeters(10L)
                .bufferStoppingPointsInMeters(20L)
                .leg(Leg.of(
                        -23.36865, -46.77967,
                        -23.36875, -46.77805
                )).build();
    }

    private void configureWith(MapLinkCredentials credentials) {
        MapLinkSDK.resetConfiguration();
        MapLinkSDK.configure()
                .with(HOMOLOG)
                .with(credentials)
                .initialize();
    }

//    private Place testPlaceCreator(String name, String id, String state, String city, String district) {
//        Point point = Point.builder()
//                .latitude(BigDecimal.valueOf(-23.368653161261896))
//                .longitude(BigDecimal.valueOf(-46.77969932556152))
//                .build();
//
//        Address address = Address.builder()
//                .state(state)
//                .city(city)
//                .district(district)
//                .street("Rua das Flores")
//                .number("23")
//                .zipcode("07700-000")
//                .point(point)
//                .build();
//
//        Set<String> phones = new HashSet<>();
//        phones.add("(11) 5080-5518");
//
//        Set<String> tags = new HashSet<>();
//        tags.add("abc123");
//        tags.add("good_place_to_live");
//
//        return Place.builder()
//                .id(id)
//                .name(name)
//                .documentNumber("444455")
//                .category(POSTOS_DE_COMBUSTIVEL)
//                .subCategory(SubCategory.POSTOS_DE_COMBUSTIVEL)
//                .address(address)
//                .phones(phones)
//                .tags(tags)
//                .active(true)
//                .build();
//    }
}