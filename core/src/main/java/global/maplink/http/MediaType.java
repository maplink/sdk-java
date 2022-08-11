package global.maplink.http;

import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public class MediaType {
    @NoArgsConstructor(access = PRIVATE)
    public static class Application {
        public static final String FORM_URLENCODED = "application/x-www-form-urlencoded";
        public static final String JSON = "application/json";
    }

    @NoArgsConstructor(access = PRIVATE)
    public static class Text {
        public static final String PLAIN = "text/plain";
    }
}
