package global.maplink.helpers;

import lombok.NoArgsConstructor;
import lombok.SneakyThrows;

import java.net.URI;
import java.net.URL;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public class UrlHelper {

    @SneakyThrows
    public static URL urlFrom(String value) {
        return new URL(value);
    }

    @SneakyThrows
    public static URI uriFrom(URL url) {
        if (url == null) return null;
        return url.toURI();
    }

}
