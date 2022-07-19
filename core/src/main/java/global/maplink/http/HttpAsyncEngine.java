package global.maplink.http;

import global.maplink.NoImplementationFoundException;
import global.maplink.http.request.Request;

import java.util.ServiceLoader;
import java.util.concurrent.CompletableFuture;

public interface HttpAsyncEngine {

    CompletableFuture<Response> run(Request request);


    static HttpAsyncEngine loadDefault() {
        return ServiceLoader.load(HttpAsyncEngine.class)
                .findFirst()
                .orElseThrow(() -> new NoImplementationFoundException(HttpAsyncEngine.class));
    }
}
