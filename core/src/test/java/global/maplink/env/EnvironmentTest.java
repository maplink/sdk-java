package global.maplink.env;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;

import java.net.MalformedURLException;
import java.net.URL;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class EnvironmentTest {

    private final Environment base = new SimpleEnvironment("http://localhost/v1");

    @Test
    public void mustBeAbleToAppendURLWithBackslash() {
        URL service = base.withService("/test");
        assertThat(service)
                .hasProtocol("http")
                .hasHost("localhost")
                .hasPath("/v1/test")
                .hasNoQuery();
    }

    @Test
    public void mustBeAbleToAppendURLWithoutBackslash() {
        URL service = base.withService("test");
        assertThat(service)
                .hasProtocol("http")
                .hasHost("localhost")
                .hasPath("/v1/test")
                .hasNoQuery();
    }

    @Test
    public void mustLoadProductionAsDefault() {
        Environment env = Environment.loadDefault();
        assertThat(env).isNotNull().isEqualTo(EnvironmentCatalog.PRODUCTION);
    }

    @Test
    public void mustEmitFailWithInvalidURL() {
        assertThatThrownBy(() ->
                new SimpleEnvironment("abc://123//abc")
                        .withService("/test")
        ).hasCauseInstanceOf(MalformedURLException.class);
    }


    @RequiredArgsConstructor
    @Getter
    private static class SimpleEnvironment implements Environment {
        private final String host;
    }
}


