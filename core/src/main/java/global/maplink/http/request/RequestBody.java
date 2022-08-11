package global.maplink.http.request;

import global.maplink.http.MediaType;
import global.maplink.json.JsonMapper;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;

import java.net.URLEncoder;
import java.util.Map;

import static global.maplink.helpers.MapHelpers.mapOf;
import static java.nio.charset.StandardCharsets.UTF_8;
import static java.util.stream.Collectors.joining;

public interface RequestBody {

    String getContentType();

    byte[] getBytes();

    @RequiredArgsConstructor
    @Getter
    class Json implements RequestBody {

        private final String contentType = MediaType.Application.JSON;
        private final byte[] bytes;

        public static <T> Json of(T object, JsonMapper mapper) {
            return new Json(mapper.toJson(object));
        }

        public static Json of(String jsonStr) {
            return new Json(jsonStr.getBytes(UTF_8));
        }
    }

    @RequiredArgsConstructor
    @Getter
    class Form implements RequestBody {

        private final String contentType = MediaType.Application.FORM_URLENCODED;

        private final byte[] bytes;

        public static Form of(Map<String, String> values) {
            return new Form(encodeToForm(values).getBytes(UTF_8));
        }

        public static Form of(String... entries) {
            return of(mapOf(entries));
        }


        private static String encodeToForm(Map<String, String> form) {
            return form.entrySet()
                    .stream()
                    .map(e -> encode(e.getKey()) + "=" + encode(e.getValue()))
                    .collect(joining("&"));
        }

        @SneakyThrows
        private static String encode(String input) {
            return URLEncoder.encode(input.trim(), UTF_8.name());
        }
    }
}
