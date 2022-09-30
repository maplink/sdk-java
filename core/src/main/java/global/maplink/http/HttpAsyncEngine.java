package global.maplink.http;

import global.maplink.NoImplementationFoundException;
import global.maplink.http.request.Request;

import java.util.ServiceLoader;
import java.util.concurrent.CompletableFuture;
import java.util.stream.StreamSupport;

public interface HttpAsyncEngine {

    CompletableFuture<Response> run(Request request);

    static HttpAsyncEngine loadDefault() {
        ServiceLoader<HttpAsyncEngine> load = ServiceLoader.load(HttpAsyncEngine.class);
        return StreamSupport.stream(load.spliterator(), false)
                .findFirst()
                .orElseThrow(() -> new NoImplementationFoundException(HttpAsyncEngine.class));
    }
}
