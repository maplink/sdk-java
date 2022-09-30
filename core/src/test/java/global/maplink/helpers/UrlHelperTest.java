package global.maplink.helpers;

import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;

import static global.maplink.helpers.UrlHelper.uriFrom;
import static global.maplink.helpers.UrlHelper.urlFrom;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class UrlHelperTest {

    public static final String VALID_URL_STR = "http://localhost:8080/teste?a=1";

    @Test
    void mustCreateUrlFromValidString() {
        URL url = urlFrom(VALID_URL_STR);
        assertThat(url).isNotNull();
        assertThat(url.getProtocol()).isEqualTo("http");
        assertThat(url.getHost()).isEqualTo("localhost");
        assertThat(url.getPort()).isEqualTo(8080);
        assertThat(url.getPath()).isEqualTo("/teste");
        assertThat(url.getQuery()).isEqualTo("a=1");
    }

    @Test
    void mustFailOnInvalidURLString() {
        assertThatThrownBy(() -> urlFrom("esta é uma url inválida"))
                .isInstanceOf(MalformedURLException.class);
    }

    @Test
    void mustReturnNullUriFromNullURL() {
        assertThat(uriFrom(null)).isNull();
    }

    @Test
    @SneakyThrows
    void mustCreateValidURIFromURL() {
        URI uri = uriFrom(new URL(VALID_URL_STR));
        assertThat(uri).isNotNull();
    }
}