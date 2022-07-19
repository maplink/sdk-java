package global.maplink.http;

import global.maplink.http.request.GetRequest;
import global.maplink.http.request.Request;

import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.concurrent.CompletableFuture;

public class HttpAsyncEngineJava11Impl implements HttpAsyncEngine {

    private final HttpClient client = HttpClient.newHttpClient();

    @Override
    public CompletableFuture<Response> run(Request request) {
        if (request instanceof GetRequest) {
            return runGet((GetRequest) request);
        }

        throw new UnsupportedOperationException(String.format("Tipo de requisição %s não é reconhecida", request.getClass().getName()));
    }

    private CompletableFuture<Response> runGet(GetRequest request) {
        var req = HttpRequest.newBuilder(request.getFullURI()).GET().build();
        return client
                .sendAsync(req, HttpResponse.BodyHandlers.ofByteArray())
                .thenApply(r -> new Response(
                        r.statusCode(),
                        r.headers().firstValue("content-type").orElse("text/plain"),
                        r.body()
                ));

    }
}
