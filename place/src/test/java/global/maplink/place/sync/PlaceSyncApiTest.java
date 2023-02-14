package global.maplink.place.sync;

import global.maplink.MapLinkSDK;
import global.maplink.credentials.InvalidCredentialsException;
import global.maplink.credentials.MapLinkCredentials;
import global.maplink.http.exceptions.MapLinkHttpException;
import global.maplink.place.schema.*;
import lombok.val;
import org.junit.jupiter.api.Test;

import static global.maplink.env.EnvironmentCatalog.HOMOLOG;
import static global.maplink.place.common.Defaults.DEFAULT_CLIENT_ID;
import static global.maplink.place.common.Defaults.DEFAULT_SECRET;
import static global.maplink.place.schema.Category.POSTOS_DE_COMBUSTIVEL;
import static global.maplink.place.utils.EnvCredentialsHelper.withEnvCredentials;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class PlaceSyncApiTest {
    @Test
    void mustBeInstantiableWithGetInstance() {
        configureWith(MapLinkCredentials.ofKey(DEFAULT_CLIENT_ID, DEFAULT_SECRET));
        val instance = PlaceSyncAPI.getInstance();
        assertThat(instance).isNotNull();
    }

    @Test
    void mustFailWithInvalidCredentials() {
        configureWith(MapLinkCredentials.ofKey(DEFAULT_CLIENT_ID, DEFAULT_SECRET));
        val instance = PlaceSyncAPI.getInstance();
        assertThatThrownBy(() -> instance.calculate(validRequest()))
                .isInstanceOf(InvalidCredentialsException.class);
    }

    @Test
    void mustFailOnInvalidRequest() {
        withEnvCredentials(credentials -> {
            configureWith(credentials);
            val instance = PlaceSyncAPI.getInstance(() -> "https://maplink.global");
            assertThatThrownBy(() -> instance.calculate(validRequest()))
                    .isInstanceOf(MapLinkHttpException.class);
        });
    }

    @Test
    void mustResolveValidCalculationRequest() {
        withEnvCredentials(credentials -> {
            configureWith(credentials);
            val instance = PlaceSyncAPI.getInstance();
            val result = instance.calculate(
                    validRequest()
            );

            assertThat(result.getTotal()).isEqualTo(1);
            assertThat(result.getLegs()).isNotEmpty().hasSize(1);
            LegResult firstLeg = result.getLegs().get(0);
            assertThat(firstLeg.getTotal()).isEqualTo(1);
            assertThat(firstLeg.getPlaces()).hasSize(1);
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