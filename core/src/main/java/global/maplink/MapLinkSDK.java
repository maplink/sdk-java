package global.maplink;

import global.maplink.credentials.MapLinkCredentials;
import global.maplink.env.Environment;
import global.maplink.extensions.SdkExtension;
import global.maplink.http.HttpAsyncEngine;
import global.maplink.json.JsonMapper;
import global.maplink.token.TokenProvider;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.val;

import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;

import static java.util.Collections.unmodifiableCollection;

@SuppressWarnings("unused")
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class MapLinkSDK {
    private static MapLinkSDK INSTANCE = null;

    private final MapLinkCredentials credentials;

    private final Environment environment;

    private final HttpAsyncEngine http;

    private final JsonMapper jsonMapper;

    private final TokenProvider tokenProvider;

    private final Collection<SdkExtension> extensions;

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

        private final Collection<SdkExtension> extensions = new HashSet<>();

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

        public Configurator with(SdkExtension extension) {
            this.extensions.add(extension);
            return this;
        }

        public void initialize() {
            if (INSTANCE != null)
                throw new IllegalStateException("MapLinkSDK already has been configured");
            val http = engine.orElseGet(HttpAsyncEngine::loadDefault);
            val jsonMapper = mapper.orElseGet(JsonMapper::loadDefault);
            val env = environment.orElseGet(Environment::loadDefault);
            INSTANCE = new MapLinkSDK(
                    credentials.orElseGet(MapLinkCredentials::loadDefault),
                    environment.orElseGet(Environment::loadDefault),
                    http,
                    jsonMapper,
                    TokenProvider.create(http, env, jsonMapper, true),
                    unmodifiableCollection(extensions)
            );
        }
    }
}
