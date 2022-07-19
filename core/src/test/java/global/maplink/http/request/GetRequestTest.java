package global.maplink.http.request;

import org.junit.jupiter.api.Test;

import java.net.MalformedURLException;
import java.net.URL;

import static org.assertj.core.api.Assertions.assertThat;

public class GetRequestTest {
    private final URL localhost;

    {
        try {
            localhost = new URL("http://localhost/teste");
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void shouldBeAbleToCreateFromRequestGet() {
        GetRequest req = Request.get(localhost);
        assertThat(req.getUrl())
                .hasProtocol(localhost.getProtocol())
                .hasHost(localhost.getHost())
                .hasPath(localhost.getPath())
                .hasNoQuery();
        assertThat(req.getFullURI())
                .hasScheme(localhost.getProtocol())
                .hasHost(localhost.getHost())
                .hasPath(localhost.getPath())
                .hasNoFragment()
                .hasNoQuery();
    }


    @Test
    public void shouldBeAbleToAddQueryParams() {
        String key = "query1";
        String value = "valor";
        GetRequest req = Request.get(localhost).withQuery(key, value);

        assertThat(req.getQuery(key)).isEqualTo(value);

        assertThat(req.getFullURI())
                .hasScheme(localhost.getProtocol())
                .hasHost(localhost.getHost())
                .hasPath(localhost.getPath())
                .hasNoFragment()
                .hasQuery(key + "=" + value);

        req = req.withQuery(key, null);

        assertThat(req.getQueries()).isEmpty();

        assertThat(req.getFullURI())
                .hasScheme(localhost.getProtocol())
                .hasHost(localhost.getHost())
                .hasPath(localhost.getPath())
                .hasNoFragment()
                .hasNoQuery();

    }
}
