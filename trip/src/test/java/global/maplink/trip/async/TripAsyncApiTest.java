package global.maplink.trip.async;

import global.maplink.MapLinkSDK;
import global.maplink.credentials.InvalidCredentialsException;
import global.maplink.credentials.MapLinkCredentials;
import global.maplink.http.exceptions.MapLinkHttpException;
import global.maplink.trip.schema.v2.problem.SitePoint;
import global.maplink.trip.schema.v2.problem.TripCalculateRequest;
import lombok.val;
import org.junit.jupiter.api.Test;

import java.util.concurrent.ExecutionException;

import static global.maplink.env.EnvironmentCatalog.HOMOLOG;
import static global.maplink.helpers.FutureHelper.await;
import static global.maplink.trip.common.Defaults.DEFAULT_CLIENT_ID;
import static global.maplink.trip.common.Defaults.DEFAULT_SECRET;
import static global.maplink.trip.utils.EnvCredentialsHelper.withEnvCredentials;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class TripAsyncApiTest {

    @Test
    void mustBeInstantiableWithGetInstance() {
        configureWith(MapLinkCredentials.ofKey(DEFAULT_CLIENT_ID, DEFAULT_SECRET));
        val instance = TripAsyncAPI.getInstance();
        assertThat(instance).isNotNull();
    }

    @Test
    void mustFailWithInvalidCredentials() {
        configureWith(MapLinkCredentials.ofKey(DEFAULT_CLIENT_ID, DEFAULT_SECRET));
        val instance = TripAsyncAPI.getInstance();
        assertThatThrownBy(() -> instance.calculate(validRequest()).get())
                .isInstanceOf(ExecutionException.class)
                .hasCauseInstanceOf(InvalidCredentialsException.class);
    }

    @Test
    void mustFailOnInvalidRequest() {
        withEnvCredentials(credentials -> {
            configureWith(credentials);
            val instance = TripAsyncAPI.getInstance(() -> "https://maplink.global");
            assertThatThrownBy(() -> instance.calculate(validRequest()).get())
                    .isInstanceOf(ExecutionException.class)
                    .hasCauseInstanceOf(MapLinkHttpException.class);
        });
    }

    @Test
    void mustResolveValidCalculationRequest() {
        withEnvCredentials(credentials -> {
            configureWith(credentials);
            val instance = TripAsyncAPI.getInstance();
            val solution = await(instance.calculate(validRequest()));
            assertThat(solution.getTotalDistance()).isNotZero();
            assertThat(solution.getAverageSpeed()).isNotZero();
            assertThat(solution.getLegs()).hasSize(1);
            val leg = solution.getLegs().get(0);
            assertThat(leg.getPoints()).isNotEmpty();
        });
    }

    private static TripCalculateRequest validRequest() {
        return TripCalculateRequest.builder()
                .point(SitePoint.of(-23.5665671, -46.6537999))
                .point(SitePoint.of(-23.5616366, -46.6561984))
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