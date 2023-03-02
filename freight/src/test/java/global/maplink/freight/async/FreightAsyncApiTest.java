package global.maplink.freight.async;

import global.maplink.MapLinkSDK;
import global.maplink.credentials.InvalidCredentialsException;
import global.maplink.credentials.MapLinkCredentials;
import global.maplink.freight.schema.FreightCalculationRequest;
import global.maplink.http.exceptions.MapLinkHttpException;
import lombok.val;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.concurrent.ExecutionException;

import static global.maplink.env.EnvironmentCatalog.HOMOLOG;
import static global.maplink.freight.common.Defaults.DEFAULT_CLIENT_ID;
import static global.maplink.freight.common.Defaults.DEFAULT_SECRET;
import static global.maplink.freight.schema.GoodsType.GRANEL_SOLIDO;
import static global.maplink.freight.schema.OperationType.A;
import static global.maplink.freight.utils.EnvCredentialsHelper.withEnvCredentials;
import static java.time.LocalDate.now;
import static java.util.Collections.singleton;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class FreightAsyncApiTest {

    @Test
    void mustBeInstantiableWithGetInstance() {
        configureWith(MapLinkCredentials.ofKey(DEFAULT_CLIENT_ID, DEFAULT_SECRET));
        val instance = FreightAsyncAPI.getInstance();
        assertThat(instance).isNotNull();
    }

    @Test
    void mustFailWithInvalidCredentials() {
        configureWith(MapLinkCredentials.ofKey(DEFAULT_CLIENT_ID, DEFAULT_SECRET));
        val instance = FreightAsyncAPI.getInstance();
        assertThatThrownBy(() -> instance.calculate(validRequest()).get())
                .isInstanceOf(ExecutionException.class)
                .hasCauseInstanceOf(InvalidCredentialsException.class);
    }


    @Test
    void mustFailOnInvalidRequest() {
        withEnvCredentials(credentials -> {
            configureWith(credentials);
            val instance = FreightAsyncAPI.getInstance(() -> "https://maplink.global");
            assertThatThrownBy(() -> instance.calculate(validRequest()).get())
                    .isInstanceOf(ExecutionException.class)
                    .hasCauseInstanceOf(MapLinkHttpException.class);
        });
    }

    @Test
    void mustResolveValidCalculationRequest() {
        withEnvCredentials(credentials -> {
            configureWith(credentials);
            val instance = FreightAsyncAPI.getInstance();
            val result = instance.calculate(
                    FreightCalculationRequest.builder()
                            .axis(singleton(9))
                            .operationType(singleton(A))
                            .goodsType(singleton(GRANEL_SOLIDO))
                            .date(now())
                            .distance(BigDecimal.valueOf(10000))
                            .build()
            ).get();
            assertThat(result.getSource()).isNotEmpty();
            assertThat(result.getResults()).containsKey(A);
            assertThat(result.getResult(A, 9, GRANEL_SOLIDO)).isNotEmpty();
            assertThat(result.getMinimumFreight()).isNotZero();
            assertThat(result.getMinimumFreightWithCosts()).isNotZero();
        });
    }

    private static FreightCalculationRequest validRequest() {
        return FreightCalculationRequest.builder()
                .date(LocalDate.now())
                .operationType(singleton(A))
                .axis(singleton(4))
                .goodsType(singleton(GRANEL_SOLIDO))
                .build();
    }

    private void configureWith(MapLinkCredentials credentials) {
        MapLinkSDK.resetConfiguration();
        MapLinkSDK.configure()
                .with(HOMOLOG)
                .with(credentials)
                .initialize();
    }
}