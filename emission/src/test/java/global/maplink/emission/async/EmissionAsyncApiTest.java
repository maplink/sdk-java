package global.maplink.emission.async;

import global.maplink.MapLinkSDK;
import global.maplink.credentials.InvalidCredentialsException;
import global.maplink.credentials.MapLinkCredentials;
import global.maplink.emission.schema.EmissionRequest;
import global.maplink.http.exceptions.MapLinkHttpException;
import lombok.val;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.concurrent.ExecutionException;

import static global.maplink.emission.common.Defaults.DEFAULT_CLIENT_ID;
import static global.maplink.emission.common.Defaults.DEFAULT_SECRET;
import static global.maplink.emission.utils.EnvCredentialsHelper.withEnvCredentials;
import static global.maplink.env.EnvironmentCatalog.HOMOLOG;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class EmissionAsyncApiTest {

    @Test
    void mustBeInstantiableWithGetInstance() {
        configureWith(MapLinkCredentials.ofKey(DEFAULT_CLIENT_ID, DEFAULT_SECRET));
        val instance = EmissionAsyncAPI.getInstance();
        assertThat(instance).isNotNull();
    }

    @Test
    void mustFailWithInvalidCredentials() {
        configureWith(MapLinkCredentials.ofKey(DEFAULT_CLIENT_ID, DEFAULT_SECRET));
        val instance = EmissionAsyncAPI.getInstance();
        assertThatThrownBy(() -> instance.calculate(EmissionRequest.builder().build()).get())
                .isInstanceOf(ExecutionException.class)
                .hasCauseInstanceOf(InvalidCredentialsException.class);
    }

    @Test
    void mustFailOnInvalidRequest() {
        withEnvCredentials(credentials -> {
            configureWith(credentials);
            val instance = EmissionAsyncAPI.getInstance(() -> "https://maplink.global");
            assertThatThrownBy(() -> instance.calculate(EmissionRequest.builder().build()).get())
                    .isInstanceOf(ExecutionException.class)
                    .hasCauseInstanceOf(MapLinkHttpException.class);
        });
    }

    @Test
    void mustResolveValidCalculationRequest() {
        withEnvCredentials(credentials -> {
            configureWith(credentials);
            val instance = EmissionAsyncAPI.getInstance();
            val result = instance.calculate(
                    "LASTROP_ESALQ",
                    "BR_GASOLINE",
                    BigDecimal.valueOf(11.3),
                    BigDecimal.valueOf(4.9),
                    80_000
            ).get();
            assertThat(result.getSource()).isEqualTo("LASTROP_ESALQ");
            assertThat(result.getFuelType()).isEqualTo("BR_GASOLINE");
            assertThat(result.getFuelConsumed()).isNotNull().isNotZero();
            assertThat(result.getTotalFuelPrice()).isNotNull().isNotZero();
            assertThat(result.getTotalEmission()).isNotNull().isNotZero();
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