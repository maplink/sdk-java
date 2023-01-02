package global.maplink.http.request;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static java.util.stream.Collectors.joining;

@SuppressWarnings("unused")
@RequiredArgsConstructor
@ToString
@EqualsAndHashCode
public abstract class Request {
    public static final String AUTHORIZATION_HEADER = "Authorization";
    @Getter
    private final URL url;

    private final Map<String, String> headers = new HashMap<>();

    private final Map<String, String> queryParams = new HashMap<>();

    public URI getFullURI() {
        try {
            return new URI(
                    url.getProtocol(),
                    url.getUserInfo(),
                    url.getHost(),
                    url.getPort(),
                    url.getPath(),
                    buildQueryString(),
                    url.getRef()
            );
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    public String getHeader(String key) {
        return headers.get(key);
    }

    public Map<String, String> getHeaders() {
        return Collections.unmodifiableMap(headers);
    }

    public Request withHeader(String key, String value) {
        if (value != null) {
            headers.put(key, value);
        } else {
            headers.remove(key);
        }
        return this;
    }

    public Request withAuthorizationHeader(String value) {
        return withHeader(AUTHORIZATION_HEADER, value);
    }

    public String getQuery(String key) {
        return queryParams.get(key);
    }

    public Map<String, String> getQueries() {
        return Collections.unmodifiableMap(queryParams);
    }

    public Request withQuery(String key, String value) {
        if (value != null) {
            queryParams.put(key, value);
        } else {
            queryParams.remove(key);
        }
        return this;
    }

    private String buildQueryString() {
        if (queryParams.isEmpty()) return null;
        return queryParams
                .entrySet()
                .stream()
                .map(e -> e.getKey() + "=" + e.getValue())
                .collect(joining("&"));
    }

    public static GetRequest get(URL url) {
        return new GetRequest(url);
    }
    public static PostRequest post(URL url, RequestBody body) {
        return new PostRequest(url, body);
    }

    public static PutRequest put(URL url, RequestBody body) {
        return new PutRequest(url, body);
    }

}
