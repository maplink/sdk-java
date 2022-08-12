package global.maplink.geocode.async;

import global.maplink.MapLinkSDK;
import global.maplink.credentials.InvalidCredentialsException;
import global.maplink.credentials.MapLinkCredentials;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.util.concurrent.ExecutionException;

import static global.maplink.env.EnvironmentCatalog.HOMOLOG;
import static global.maplink.geocode.common.Defaults.DEFAULT_CLIENT_ID;
import static global.maplink.geocode.common.Defaults.DEFAULT_SECRET;
import static global.maplink.geocode.common.Type.ZIPCODE;
import static global.maplink.geocode.utils.EnvCredentialsHelper.withEnvCredentials;
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

    private void configureWith(MapLinkCredentials credentials) {
        MapLinkSDK.resetConfiguration();
        MapLinkSDK.configure()
                .with(HOMOLOG)
                .with(credentials)
                .initialize();
    }
}
