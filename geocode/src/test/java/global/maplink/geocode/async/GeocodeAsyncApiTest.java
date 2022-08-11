package global.maplink.geocode.async;

import global.maplink.MapLinkSDK;
import global.maplink.credentials.InvalidCredentialsException;
import global.maplink.credentials.MapLinkCredentials;
import global.maplink.credentials.NoCredentialsProvidedException;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.util.concurrent.ExecutionException;

import static global.maplink.env.EnvironmentCatalog.HOMOLOG;
import static global.maplink.geocode.common.Defaults.DEFAULT_CLIENT_ID;
import static global.maplink.geocode.common.Defaults.DEFAULT_SECRET;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@Slf4j
public class GeocodeAsyncApiTest {


    @AfterEach
    public void cleanupSDK() {
        MapLinkSDK.resetConfiguration();
    }

    @Test
    public void mustBeInstantiableWithGetInstance() {
        configureWith(MapLinkCredentials.ofKey(DEFAULT_CLIENT_ID, DEFAULT_SECRET));
        val instance = GeocodeAsyncAPI.getInstance();
        assertThat(instance).isNotNull();
    }

    @Test
    public void mustFailWithInvalidCredentials() {
        configureWith(MapLinkCredentials.ofKey(DEFAULT_CLIENT_ID, DEFAULT_SECRET));
        val instance = GeocodeAsyncAPI.getInstance();
        assertThatThrownBy(() -> instance.suggestions("Rua Afonso Celso").get())
                .isInstanceOf(ExecutionException.class)
                .hasCauseInstanceOf(InvalidCredentialsException.class);
    }

    @Test
    @SneakyThrows
    public void mustReturnAutocompleteSimpleQueryCorrectly() {
        MapLinkCredentials credentials;
        try {
            credentials = MapLinkCredentials.loadDefault();
        } catch (NoCredentialsProvidedException e) {
            log.warn("Valid credentials not provided, ignoring tests {}", e.getMessage());
            return;
        }
        configureWith(credentials);
        val instance = GeocodeAsyncAPI.getInstance();
        val result = instance.suggestions("Rua Afonso Celso").get();
        assertThat(result.getResults()).isNotEmpty();
        assertThat(result.getFound()).isNotZero();
    }

    private void configureWith(MapLinkCredentials credentials) {
        MapLinkSDK.resetConfiguration();
        MapLinkSDK.configure()
                .with(HOMOLOG)
                .with(credentials)
                .initialize();
    }
}
