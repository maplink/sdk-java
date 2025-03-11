package global.maplink.geocode.ext.gmaps.suggestions;

import global.maplink.geocode.schema.v1.GeoPoint;
import global.maplink.geocode.schema.v1.suggestions.SuggestionsResult;
import global.maplink.geocode.schema.v1.Address;
import global.maplink.geocode.schema.v1.Type;
import global.maplink.geocode.schema.v1.suggestions.Suggestion;
import global.maplink.helpers.UrlHelper;
import global.maplink.json.JsonMapper;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Set;

import static global.maplink.geocode.ext.gmaps.suggestions.GeocodeGMapsResponse.STATUS_EMPTY;
import static global.maplink.geocode.ext.gmaps.suggestions.GeocodeGMapsResponse.STATUS_OK;
import static java.util.Arrays.asList;
import static java.util.Optional.ofNullable;
import static org.assertj.core.api.Assertions.assertThat;

class GeocodeGMapsResponseTest {

    private static final String EMPTY_RESPONSE_FILE = "gmaps-empty-response.json";
    private static final String SAMPLE_RESPONSE_FILE = "gmaps-sample-response.json";
    private static final String FULL_RESPONSE_FILE = "gmaps-full-response.json";
    public static final String SAMPLE_LABEL = "Paraná, Brasil";
    public static final String FULL_LABEL = "Edifício Silver Tower - Alameda Campinas, 579 - Jardim Paulista, São Paulo - SP, 01404-100, Brasil";

    private final JsonMapper mapper = JsonMapper.loadDefault();

    @Test
    void mustResultEmptySuggestionResultWhenEmpty() {
        GeocodeGMapsResponse response = new GeocodeGMapsResponse();
        assertThat(response.toSuggestions()).isEqualTo(
                SuggestionsResult.EMPTY);
    }

    @Test
    void mustResultEmptySuggestionResultWhenEmptyJsonResult() {
        byte[] bytes = readResponseFile(EMPTY_RESPONSE_FILE);
        GeocodeGMapsResponse response = mapper.fromJson(bytes, GeocodeGMapsResponse.class);
        assertThat(response).isNotNull();
        assertThat(response.getResults()).isEmpty();
        assertThat(response.getStatus()).isEqualTo(STATUS_EMPTY);
        assertThat(response.isOk()).isFalse();
        assertThat(response.isDenied()).isFalse();
        assertThat(response.isEmpty()).isTrue();

        assertThat(response.toSuggestions()).isEqualTo(
                SuggestionsResult.EMPTY);
    }

    @Test
    void mustFillSuggestionResultFromSampleJson() {
        byte[] bytes = readResponseFile(SAMPLE_RESPONSE_FILE);

        GeocodeGMapsResponse response = mapper.fromJson(bytes, GeocodeGMapsResponse.class);
        assertThat(response).isNotNull();
        assertThat(response.getResults()).hasSize(2);
        assertThat(response.getStatus()).isEqualTo(STATUS_OK);
        assertThat(response.isOk()).isTrue();
        assertThat(response.isDenied()).isFalse();
        assertThat(response.isEmpty()).isFalse();

        GeocodeGMapsResult firstResult = response.getResults().get(0);
        assertThat(firstResult.getFormatted_address()).isEqualTo(SAMPLE_LABEL);
        assertThat(firstResult.getGeometry().getLocation_type()).isEqualTo("APPROXIMATE");
        assertThat(firstResult.getAddress_components()).hasSize(2).containsExactlyInAnyOrder(
                new GMapsAddressComponent(
                        "Paraná",
                        "PR",
                        setOf("administrative_area_level_1", "political")
                ),
                new GMapsAddressComponent(
                        "Brasil",
                        "BR",
                        setOf("country", "political")
                )
        );

        SuggestionsResult suggestions = response.toSuggestions();

        assertThat(suggestions).isNotNull().isNotEqualTo(SuggestionsResult.EMPTY);
        assertThat(suggestions.getFound()).isEqualTo(2);
        assertThat(suggestions.getResults()).hasSize(2);
        Suggestion mostRelevant = suggestions.getMostRelevant();
        assertThat(mostRelevant.getId()).isEqualTo("place-result1");
        assertThat(mostRelevant.getLabel()).isEqualTo(SAMPLE_LABEL);
        assertThat(mostRelevant.getType()).isEqualTo(Type.STATE);
        Address address = mostRelevant.getAddress();
        assertThat(address.getCountry()).isEqualTo("Brasil");
        assertThat(address.getState().getCode()).isEqualTo("PR");
        assertThat(address.getState().getName()).isEqualTo("Paraná");
        assertThat(address.getDistrict()).isNull();
        assertThat(address.getRoad()).isNull();
        assertThat(address.getNumber()).isNull();
        assertThat(address.getZipCode()).isNull();
        assertThat(address.getMainLocation()).isEqualTo(GeoPoint.of(5.5, 2.5));
    }

    @Test
    void mustFillSuggestionResultFromFullJson() {
        JsonMapper mapper = JsonMapper.loadDefault();
        byte[] bytes = readResponseFile(FULL_RESPONSE_FILE);

        GeocodeGMapsResponse response = mapper.fromJson(bytes, GeocodeGMapsResponse.class);
        assertThat(response).isNotNull();
        assertThat(response.getResults()).hasSize(1);
        assertThat(response.getStatus()).isEqualTo(STATUS_OK);
        assertThat(response.isOk()).isTrue();
        assertThat(response.isDenied()).isFalse();
        assertThat(response.isEmpty()).isFalse();

        SuggestionsResult suggestions = response.toSuggestions();

        assertThat(suggestions).isNotNull().isNotEqualTo(SuggestionsResult.EMPTY);
        assertThat(suggestions.getFound()).isEqualTo(1);
        assertThat(suggestions.getResults()).hasSize(1);
        Suggestion mostRelevant = suggestions.getMostRelevant();
        assertThat(mostRelevant.getId()).isEqualTo("maplink-addr");
        assertThat(mostRelevant.getLabel()).isEqualTo(FULL_LABEL);
        assertThat(mostRelevant.getType()).isEqualTo(Type.ROAD);
        Address address = mostRelevant.getAddress();
        assertThat(address.getCountry()).isEqualTo("Brasil");
        assertThat(address.getState().getCode()).isEqualTo("SP");
        assertThat(address.getState().getName()).isEqualTo("São Paulo");
        assertThat(address.getDistrict()).isEqualTo("Jardim Paulista");
        assertThat(address.getRoad()).isEqualTo("Alameda Campinas");
        assertThat(address.getNumber()).isEqualTo("579");
        assertThat(address.getZipCode()).isEqualTo("01404-100");
        assertThat(address.getMainLocation()).isEqualTo(GeoPoint.of(10, 5));
    }

    private Set<String> setOf(String... values) {
        return new HashSet<>(asList(values));
    }

    @SneakyThrows
    private byte[] readResponseFile(String file) {
        return ofNullable(getClass().getClassLoader().getResource(file))
                .map(UrlHelper::uriFrom)
                .map(Paths::get)
                .map(this::readAllBytes)
                .orElseThrow(IllegalStateException::new);
    }

    @SneakyThrows
    private byte[] readAllBytes(Path path) {
        return Files.readAllBytes(path);
    }
}