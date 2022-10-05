package global.maplink.geocode.async;

import global.maplink.MapLinkSDK;
import global.maplink.credentials.InvalidCredentialsException;
import global.maplink.credentials.MapLinkCredentials;
import global.maplink.geocode.schema.reverse.ReverseRequest.Entry;
import global.maplink.geocode.schema.structured.StructuredRequest;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.concurrent.ExecutionException;

import static global.maplink.env.EnvironmentCatalog.HOMOLOG;
import static global.maplink.geocode.common.Defaults.DEFAULT_CLIENT_ID;
import static global.maplink.geocode.common.Defaults.DEFAULT_SECRET;
import static global.maplink.geocode.schema.Type.ZIPCODE;
import static global.maplink.geocode.schema.crossCities.CrossCitiesRequest.point;
import static global.maplink.geocode.schema.reverse.ReverseRequest.entry;
import static global.maplink.geocode.schema.structured.StructuredRequest.multi;
import static global.maplink.geocode.utils.EnvCredentialsHelper.withEnvCredentials;
import static java.util.stream.Collectors.toList;
import static java.util.stream.IntStream.range;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@Slf4j
public class GeocodeAsyncApiTest {

    @AfterEach
    void cleanupSDK() {
        MapLinkSDK.resetConfiguration();
    }

    @Test
    void mustBeInstantiableWithGetInstance() {
        configureWith(MapLinkCredentials.ofKey(DEFAULT_CLIENT_ID, DEFAULT_SECRET));
        val instance = GeocodeAsyncAPI.getInstance();
        assertThat(instance).isNotNull();
    }

    @Test
    void mustFailWithInvalidCredentials() {
        configureWith(MapLinkCredentials.ofKey(DEFAULT_CLIENT_ID, DEFAULT_SECRET));
        val instance = GeocodeAsyncAPI.getInstance();
        assertThatThrownBy(() -> instance.suggestions("Rua Afonso Celso").get())
                .isInstanceOf(ExecutionException.class)
                .hasCauseInstanceOf(InvalidCredentialsException.class);
    }

    @Test
    void mustReturnAutocompleteWithTypeQueryCorrectly() {
        withEnvCredentials(credentials -> {
            configureWith(credentials);
            val instance = GeocodeAsyncAPI.getInstance();
            val result = instance.suggestions("São Paulo", ZIPCODE).get();
            assertThat(result.getResults()).isNotEmpty();
            assertThat(result.getFound()).isNotZero();
            assertThat(result.getResults()).allMatch(v -> v.getType() == ZIPCODE);
        });
    }

    @Test
    void mustReturnAutocompleteWithoutTypeQueryCorrectly() {
        withEnvCredentials(credentials -> {
            configureWith(credentials);
            val instance = GeocodeAsyncAPI.getInstance();
            val result = instance.suggestions("Rua Afonso Celso").get();
            assertThat(result.getResults()).isNotEmpty();
            assertThat(result.getFound()).isNotZero();
        });
    }

    @Test
    void mustReturnSuggestionsOnSingleGeocode() {
        withEnvCredentials(credentials -> {
            configureWith(credentials);
            val instance = GeocodeAsyncAPI.getInstance();
            val result = instance.structured(
                    StructuredRequest.ofCity("sp", "Paulo", "SP")
            ).get();
            assertThat(result.getResults()).hasSizeGreaterThan(1);
            assertThat(result.getFound()).isGreaterThan(1);
            assertThat(result.getById("sp")).isNotEmpty();
        });
    }

    @Test
    void mustReturnOneResultByRequestInMultiGeocode() {
        withEnvCredentials(credentials -> {
            configureWith(credentials);
            val instance = GeocodeAsyncAPI.getInstance();
            val result = instance.structured(multi(
                    StructuredRequest.ofCity("sp", "São Paulo", "SP"),
                    StructuredRequest.ofCity("pr", "Paraná", "PR"),
                    StructuredRequest.of("addr")
                            .state("SP")
                            .city("São Paulo")
                            .district("Jardim Paulista")
                            .road("Alameda Campinas")
                            .number(579)
                            .build()

            )).get();
            assertThat(result.getResults()).hasSize(3);
            assertThat(result.getFound()).isEqualTo(3);
            assertThat(result.getById("sp")).isNotEmpty();
            assertThat(result.getById("pr")).isNotEmpty();
            assertThat(result.getById("addr")).isNotEmpty();
        });
    }

    @Test
    void mustReturnOneResultByRequestInReverse() {
        withEnvCredentials(credentials -> {
            configureWith(credentials);
            val instance = GeocodeAsyncAPI.getInstance();
            val result = instance.reverse(
                    entry(-22.9141308, -43.445982),
                    entry("sp", -23.6818334, -46.8823662),
                    entry("pr", -25.494945, -49.3598374, 500),
                    Entry.builder()
                            .id("addr")
                            .lat(BigDecimal.valueOf(-23.5666682))
                            .lon(BigDecimal.valueOf(-46.6558011))
                            .build()

            ).get();
            assertThat(result.getResults()).hasSize(4);
            assertThat(result.getFound()).isEqualTo(4);
            assertThat(result.getById("sp")).isNotEmpty();
            assertThat(result.getById("pr")).isNotEmpty();
            assertThat(result.getById("addr")).isNotEmpty();
        });
    }

    @Test
    void mustReturnCrossedCities() {
        withEnvCredentials(credentials -> {
            configureWith(credentials);
            val instance = GeocodeAsyncAPI.getInstance();
            val result = instance.crossCities(
                    point(-22.9141308, -43.445982),
                    point(-23.6818334, -46.8823662),
                    point(-25.494945, -49.3598374)
            ).get();
            assertThat(result.getResults()).hasSize(35);
            assertThat(result.getFound()).isEqualTo(35);
        });
    }

    @Test
    void mustAllowAbove200PointsInReverse() {
        withEnvCredentials(credentials -> {
            configureWith(credentials);
            val instance = GeocodeAsyncAPI.getInstance();
            val entries = range(0, 500)
                    .mapToObj(i -> entry(
                            "id-" + i,
                            -22.9141308 + (i * 0.000001),
                            -43.445982 + (i * 0.000001)
                    )).collect(toList());
            val result = instance.reverse(entries).get();
            assertThat(result.getResults()).hasSize(entries.size());
            assertThat(result.getFound()).isEqualTo(entries.size());
            for (val entry : entries) {
                assertThat(result.getById(entry.getId())).isNotEmpty();
            }
        });
    }

    private void configureWith(MapLinkCredentials credentials) {
        MapLinkSDK.resetConfiguration();
        MapLinkSDK.configure()
                .with(HOMOLOG)
                .with(credentials)
                .initialize();
    }

}
