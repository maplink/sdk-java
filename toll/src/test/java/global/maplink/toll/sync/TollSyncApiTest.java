package global.maplink.toll.sync;

import global.maplink.MapLinkSDK;
import global.maplink.credentials.InvalidCredentialsException;
import global.maplink.credentials.MapLinkCredentials;
import global.maplink.http.exceptions.MapLinkHttpException;
import global.maplink.toll.schema.request.LegRequest;
import lombok.val;
import org.junit.jupiter.api.Test;

import static global.maplink.env.EnvironmentCatalog.HOMOLOG;
import static global.maplink.toll.common.Defaults.DEFAULT_CLIENT_ID;
import static global.maplink.toll.common.Defaults.DEFAULT_SECRET;
import static global.maplink.toll.schema.Billing.FREE_FLOW;
import static global.maplink.toll.schema.TollVehicleType.CAR;
import static global.maplink.toll.utils.EnvCredentialsHelper.withEnvCredentials;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class TollSyncApiTest {

    @Test
    void mustBeInstantiableWithGetInstance() {
        configureWith(MapLinkCredentials.ofKey(DEFAULT_CLIENT_ID, DEFAULT_SECRET));
        val instance = TollSyncAPI.getInstance();
        assertThat(instance).isNotNull();
    }

    @Test
    void mustFailWithInvalidCredentials() {
        configureWith(MapLinkCredentials.ofKey(DEFAULT_CLIENT_ID, DEFAULT_SECRET));
        val instance = TollSyncAPI.getInstance();
        assertThatThrownBy(() -> instance.calculate(LegRequest.builder().build()))
                .isInstanceOf(InvalidCredentialsException.class);
    }

    @Test
    void mustFailOnInvalidRequest() {
        withEnvCredentials(credentials -> {
            configureWith(credentials);
            val instance = TollSyncAPI.getInstance(() -> "https://maplink.global");
            assertThatThrownBy(() -> instance.calculate(LegRequest.builder().build()))
                    .isInstanceOf(MapLinkHttpException.class);
        });
    }

    @Test
    void mustResolveValidCalculationRequest() {
        withEnvCredentials(credentials -> {
            configureWith(credentials);
            val instance = TollSyncAPI.getInstance();
            val result = instance.calculate(
                    LegRequest.of(
                            CAR,
                            -22.05491, -42.36299,
                            -22.05461, -42.36224
                    )
            );
            assertThat(result.getTotalCost()).isNotZero();
            assertThat(result.getLegs()).hasSize(1);
        });
    }

    @Test
    void mustResolveValidCalculationRequestwithBilling() {
        withEnvCredentials(credentials -> {
            configureWith(credentials);
            val instance = TollSyncAPI.getInstance();
            val result = instance.calculate(
                    FREE_FLOW,
                    LegRequest.of(
                            CAR,
                            -22.05491, -42.36299,
                            -22.05461, -42.36224
                    )
            );
            assertThat(result.getTotalCost()).isNotZero();
            assertThat(result.getLegs()).hasSize(1);
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