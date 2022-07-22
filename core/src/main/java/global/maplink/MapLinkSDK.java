package global.maplink;

import global.maplink.credentials.MapLinkCredentials;
import global.maplink.env.Environment;
import global.maplink.http.HttpAsyncEngine;
import global.maplink.json.JsonMapper;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class MapLinkSDK {
    private static MapLinkSDK INSTANCE = null;

    private final MapLinkCredentials credentials;

    private final Environment environment;

    private final HttpAsyncEngine http;

    private final JsonMapper jsonMapper;

    public static Configurator configure() {
        return new Configurator();
    }

    public static MapLinkSDK getInstance() {
        if (INSTANCE == null) {
            throw new MapLinkNotConfiguredException();
        }
        return INSTANCE;
    }

    public static void resetConfiguration() {
        INSTANCE = null;
    }


    @SuppressWarnings("OptionalUsedAsFieldOrParameterType")
    public static class Configurator {

        private Optional<MapLinkCredentials> credentials = Optional.empty();

        private Optional<Environment> environment = Optional.empty();

        private Optional<HttpAsyncEngine> engine = Optional.empty();

        private Optional<JsonMapper> mapper = Optional.empty();

        public Configurator with(MapLinkCredentials credentials) {
            this.credentials = Optional.of(credentials);
            return this;
        }

        public Configurator with(HttpAsyncEngine engine) {
            this.engine = Optional.of(engine);
            return this;
        }

        public Configurator with(Environment environment) {
            this.environment = Optional.of(environment);
            return this;
        }

        public Configurator with(JsonMapper mapper) {
            this.mapper = Optional.of(mapper);
            return this;
        }

        public MapLinkSDK initialize() {
            if (INSTANCE != null)
                throw new IllegalStateException("MapLinkSDK already has been configured");

            INSTANCE = new MapLinkSDK(
                    credentials.orElseGet(MapLinkCredentials::loadDefault),
                    environment.orElseGet(Environment::loadDefault),
                    engine.orElseGet(HttpAsyncEngine::loadDefault),
                    mapper.orElseGet(JsonMapper::loadDefault)
            );
            return INSTANCE;
        }
    }
}
