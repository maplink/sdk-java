package global.maplink.place.sync;

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

import static global.maplink.env.EnvironmentCatalog.HOMOLOG;
import static global.maplink.place.common.Defaults.DEFAULT_CLIENT_ID;
import static global.maplink.place.common.Defaults.DEFAULT_SECRET;
import static global.maplink.place.schema.Category.POSTOS_DE_COMBUSTIVEL;
import static global.maplink.place.utils.EnvCredentialsHelper.withEnvCredentials;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class PlaceSyncApiTest {
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
            val instance = PlaceSyncAPI.getInstance();

            instance.create(placeSP1);
            instance.create(placeSP2);
            instance.create(placeRJ1);

            Place placeSp1Read = instance.getByOriginId("1a2b3c").get();
            Place placeSp2Read = instance.getByOriginId("1a2b3d").get();
            Place placeRj1Read = instance.getByOriginId("1a2b3e").get();

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
            val instance = PlaceSyncAPI.getInstance();

            ListAllStatesRequest request = ListAllStatesRequest.builder().build();
            List<String> statesResulted = instance.listAllStates(request);
            assertThat(statesResulted.toString()).isEqualTo(TestPlaceUtils.LIST_ALL_STATES_EXPECTED_RESULT);
        });
    }

    @Order(3)
    @SneakyThrows
    @Test
    void shouldListAllCitiesFormSP() {
        withEnvCredentials(credentials -> {
            configureWith(credentials);
            val instance = PlaceSyncAPI.getInstance();

            ListAllCitiesRequest request = ListAllCitiesRequest.builder()
                    .state("SP")
                    .build();
            List<String> citiesResulted = instance.listAllCities(request);
            assertThat(citiesResulted.toString()).contains("Santos");
            assertThat(citiesResulted.toString()).contains("São Paulo");
        });
    }

    @Order(4)
    @Test
    void shouldListAllDistrictsFromSantosSp() {
        withEnvCredentials(credentials -> {
            configureWith(credentials);
            val instance = PlaceSyncAPI.getInstance();

            ListAllDistrictsRequest request = ListAllDistrictsRequest.builder()
                    .state("SP")
                    .city("Santos")
                    .build();
            List<String> districtsResulted = instance.listAllDistricts(request);
            System.out.println("districtsResulted -->> " + districtsResulted.toString());
            assertThat(districtsResulted.toString()).isEqualTo(TestPlaceUtils.LIST_ALL_DISTRICTS_EXPECTED_RESULT);
        });
    }

    @Order(5)
    @Test
    void mustBeInstantiableWithGetInstance() {
        configureWith(MapLinkCredentials.ofKey(DEFAULT_CLIENT_ID, DEFAULT_SECRET));
        val instance = PlaceSyncAPI.getInstance();
        assertThat(instance).isNotNull();
    }

    @Order(6)
    @Test
    void mustFailWithInvalidCredentials() {
        configureWith(MapLinkCredentials.ofKey(DEFAULT_CLIENT_ID, DEFAULT_SECRET));
        val instance = PlaceSyncAPI.getInstance();
        assertThatThrownBy(() -> instance.calculate(validRequest()))
                .isInstanceOf(InvalidCredentialsException.class);
    }

    @Order(7)
    @Test
    void mustFailOnInvalidRequest() {
        withEnvCredentials(credentials -> {
            configureWith(credentials);
            val instance = PlaceSyncAPI.getInstance(() -> "https://maplink.global");
            assertThatThrownBy(() -> instance.calculate(validRequest()))
                    .isInstanceOf(MapLinkHttpException.class);
        });
    }

    @Order(8)
    @Test
    void mustResolveValidCalculationRequest() {
        withEnvCredentials(credentials -> {
            configureWith(credentials);
            val instance = PlaceSyncAPI.getInstance();
            val result = instance.calculate(
                    validRequest()
            );

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
}