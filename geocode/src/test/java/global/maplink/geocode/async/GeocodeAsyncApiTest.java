package global.maplink.geocode.async;

import global.maplink.MapLinkSDK;
import global.maplink.credentials.InvalidCredentialsException;
import global.maplink.credentials.MapLinkCredentials;
import global.maplink.geocode.schema.SingleBase;
import global.maplink.geocode.schema.v1.cities.CitiesByStateRequest;
import global.maplink.geocode.schema.v1.structured.StructuredRequest;
import global.maplink.geocode.schema.v1.suggestions.SuggestionsRequest;
import global.maplink.geocode.schema.v1.suggestions.SuggestionsResult;
import global.maplink.geocode.schema.v2.reverse.ReverseBaseRequest;
import global.maplink.http.exceptions.MapLinkHttpException;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;
import java.util.concurrent.ExecutionException;

import static global.maplink.env.EnvironmentCatalog.HOMOLOG;
import static global.maplink.geocode.common.Defaults.DEFAULT_CLIENT_ID;
import static global.maplink.geocode.common.Defaults.DEFAULT_SECRET;
import static global.maplink.geocode.schema.v1.TypeVersionOne.CITY;
import static global.maplink.geocode.schema.v1.TypeVersionOne.ZIPCODE;
import static global.maplink.geocode.schema.v1.crossCities.CrossCitiesRequest.point;
import static global.maplink.geocode.schema.v1.reverse.ReverseRequest.entry;
import static global.maplink.geocode.schema.v1.structured.StructuredRequest.*;
import static global.maplink.geocode.schema.v1.structured.StructuredRequest.multi;
import static global.maplink.geocode.utils.EnvCredentialsHelper.withEnvCredentials;
import static java.util.stream.Collectors.toList;
import static java.util.stream.IntStream.range;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;


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
    void mustFailOnInvalidRequest() {
        withEnvCredentials(credentials -> {
            configureWith(credentials);
            val instance = GeocodeAsyncAPI.getInstance(() -> "https://pre-api.maplink.global/fakepath/");
            assertThatThrownBy(() ->
                    instance.suggestions("São Paulo", ZIPCODE).get()
            ).isInstanceOf(ExecutionException.class)
                    .hasCauseInstanceOf(MapLinkHttpException.class);
        });
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
    void mustReturnAutocompleteExactlyNumberWithLastMileQuery() {
        withEnvCredentials(credentials -> {
            configureWith(credentials);
            val instance = GeocodeAsyncAPI.getInstance();
            val result = instance.suggestions(SuggestionsRequest.builder()
                    .query("Alameda Campinas, 579 - Jardim Paulista")
                    .lastMile(true)
                    .build()).get();
            assertEquals("579", result.getResults().get(0).getAddress().getNumber());
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
                    ofCity("sp", "Paulo", "SP")
            ).get();
            assertThat(result.getResults()).hasSizeGreaterThan(1);
            assertThat(result.getFound()).isGreaterThan(1);
            assertThat(result.getById("sp")).isNotEmpty();
        });
    }

    @Test
    void mustReturnExactlyNumberWithLastMileQueryByRequestInSingleGeocode() {
        withEnvCredentials(credentials -> {
            configureWith(credentials);
            val instance = GeocodeAsyncAPI.getInstance();
            SuggestionsResult result = instance.structured(StructuredRequest.Single.builder().id("reqId")
                    .state("sp")
                    .city("sao paulo")
                    .road("alameda campinas")
                    .number(579)
                    .lastMile(true)
                    .build()).get();
            assertEquals("579", result.getResults().get(0).getAddress().getNumber());
        });
    }

    @Test
    void mustReturnSuggestionsOnCitiesByState() {
        withEnvCredentials(credentials -> {
            configureWith(credentials);
            val instance = GeocodeAsyncAPI.getInstance();
            val result = instance.citiesByState(
                    CitiesByStateRequest.builder().state("SC").build()
            ).get();
            assertThat(result.getResults()).hasSizeGreaterThan(1);
            assertThat(result.getFound()).isGreaterThanOrEqualTo(200);
            assertThat(result.getResults().get(0).getAddress().getState().getName()).isEqualTo("SANTA CATARINA");
        });
    }

    @Test
    void mustReturnSuggestionsOnCitiesByStateDefault() {
        withEnvCredentials(credentials -> {
            configureWith(credentials);
            val instance = GeocodeAsyncAPI.getInstance();
            val result = instance.citiesByState("SC").get();
            assertThat(result.getResults()).hasSizeGreaterThan(1);
            assertThat(result.getFound()).isGreaterThanOrEqualTo(200);
            assertThat(result.getResults().get(0).getAddress().getState().getName()).isEqualTo("SANTA CATARINA");
        });
    }

    @Test
    void mustReturnOneResultByRequestInMultiGeocode() {
        withEnvCredentials(credentials -> {
            configureWith(credentials);
            val instance = GeocodeAsyncAPI.getInstance();
            val result = instance.structured(multi(
                    ofCity("sp", "São Paulo", "SP"),
                    ofCity("pr", "Paraná", "PR"),
                    of("addr")
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
    void mustReturnExactlyNumberWithLastMileQueryByRequestInMultiGeocode() {
        withEnvCredentials(credentials -> {
            configureWith(credentials);
            val instance = GeocodeAsyncAPI.getInstance();
            val multiRequest = multi(
                    of("sc")
                            .state("sc")
                            .city("itajai")
                            .road("rua leopoldo hess")
                            .number(75)
                            .build(),
                    of("pr")
                            .state("pr")
                            .city("ponta grossa")
                            .road("rua citrino")
                            .number(82)
                            .build()
            );
            multiRequest.setLastMile(true);

            val result = instance.structured(multiRequest).get();
            result.getById("sc").ifPresent(spResult ->
                    assertEquals("75", spResult.getAddress().getNumber())
            );
            result.getById("pr").ifPresent(prResult ->
                    assertEquals("82", prResult.getAddress().getNumber())
            );
        });
    }

    @Test
    void mustAllowAbove200PointsInStructuredMulti() {
        withEnvCredentials(credentials -> {
            configureWith(credentials);
            val instance = GeocodeAsyncAPI.getInstance();
            List<SingleBase> requests = range(0, 500)
                    .mapToObj(i -> of("id-" + i)
                            .type(CITY)
                            .city("sao paulo " + i)
                            .build()
                    ).collect(toList());
            val result = instance.structured(multi(requests)).get();
            assertThat(result.getResults()).hasSize(requests.size());
            assertThat(result.getFound()).isEqualTo(requests.size());
            for (val request : requests) {
                assertThat(result.getById(request.getId())).isNotEmpty();
            }
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
                    ReverseBaseRequest.Entry.builder()
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

    private void configureWith(MapLinkCredentials credentials) {
        MapLinkSDK.resetConfiguration();
        MapLinkSDK.configure()
                .with(HOMOLOG)
                .with(credentials)
                .initialize();
    }

}
