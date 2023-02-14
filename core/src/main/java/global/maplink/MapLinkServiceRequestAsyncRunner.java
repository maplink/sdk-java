package global.maplink;

import global.maplink.credentials.MapLinkCredentials;
import global.maplink.env.Environment;
import global.maplink.http.HttpAsyncEngine;
import global.maplink.json.JsonMapper;
import global.maplink.token.TokenProvider;
import lombok.RequiredArgsConstructor;

import java.lang.invoke.MethodHandles;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.concurrent.CompletableFuture;

import static java.lang.String.format;
import static java.lang.System.identityHashCode;

public interface MapLinkServiceRequestAsyncRunner {

    <T> CompletableFuture<T> run(MapLinkServiceRequest<T> request);

    Environment getEnvironment();

    static MapLinkServiceRequestAsyncRunner createRunner(
            Environment environment,
            HttpAsyncEngine http,
            JsonMapper mapper,
            TokenProvider tokenProvider,
            MapLinkCredentials credentials
    ) {
        return new MapLinkServiceRequestAsyncRunnerImpl(environment, http, mapper, tokenProvider, credentials);
    }

    @SuppressWarnings("unchecked")
    static <T> T proxyFor(
            Class<T> apiClass,
            Environment environment,
            HttpAsyncEngine http,
            JsonMapper mapper,
            TokenProvider tokenProvider,
            MapLinkCredentials credentials
    ) {
        MapLinkServiceRequestAsyncRunner runner = createRunner(environment, http, mapper, tokenProvider, credentials);
        return (T) Proxy.newProxyInstance(
                apiClass.getClassLoader(),
                new Class[]{apiClass},
                new ProxyApiImpl<>(apiClass, runner)
        );
    }

    @RequiredArgsConstructor
    class ProxyApiImpl<T> implements InvocationHandler {

        private final Class<T> apiClass;

        private final MapLinkServiceRequestAsyncRunner runner;

        private final MethodHandles.Lookup lookup = MethodHandles.lookup();

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            if (method.isDefault()) {
                return invokeDefault(proxy, method, args);
            }
            if (method.getName().equals("equals")) {
                return proxy == args[0];
            }
            if (method.getName().equals("hashCode")) {
                return identityHashCode(proxy);
            }
            if (method.getName().equals("toString")) {
                return format("%s at %s", apiClass.getName(), runner.getEnvironment());
            }
            if (args.length == 1 && args[0] instanceof MapLinkServiceRequest) {
                MapLinkServiceRequest<?> request = (MapLinkServiceRequest<?>) args[0];
                return runner.run(request);
            }
            throw new UnsupportedOperationException();
        }

        private Object invokeDefault(Object proxy, Method method, Object[] args) throws Throwable {
            return lookup
                    .unreflectSpecial(method, method.getDeclaringClass())
                    .bindTo(proxy)
                    .invokeWithArguments(args);
        }
    }
}
