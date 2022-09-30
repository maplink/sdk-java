package global.maplink.http;

import global.maplink.http.request.GetRequest;
import global.maplink.http.request.PostRequest;
import global.maplink.http.request.Request;
import lombok.val;

import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.concurrent.CompletableFuture;

public class HttpAsyncEngineJava11Impl implements HttpAsyncEngine {

    public static final String HEADER_CONTENT_TYPE = "content-type";

    private final HttpClient client = HttpClient.newHttpClient();

    @Override
    public CompletableFuture<Response> run(Request request) {
        if (request instanceof GetRequest) return runGet((GetRequest) request);
        if (request instanceof PostRequest) return runPost((PostRequest) request);

        throw new UnsupportedOperationException(String.format("Tipo de requisição %s não é reconhecida", request.getClass().getName()));
    }

    private HttpRequest.Builder buildBaseRequest(Request request) {
        var builder = HttpRequest.newBuilder(request.getFullURI());
        request.getHeaders().forEach(builder::header);
        return builder;
    }

    private Response translateResponse(HttpResponse<byte[]> response) {
        return new Response(
                response.statusCode(),
                response.headers().firstValue(HEADER_CONTENT_TYPE).orElse(MediaType.Text.PLAIN),
                response.body()
        );
    }

    private CompletableFuture<Response> runGet(GetRequest request) {
        val req = buildBaseRequest(request)
                .GET()
                .build();
        return client
                .sendAsync(req, BodyHandlers.ofByteArray())
                .thenApply(this::translateResponse);
    }

    private CompletableFuture<Response> runPost(PostRequest request) {
        val req = buildBaseRequest(request)
                .POST(BodyPublishers.ofByteArray(request.getBody()))
                .build();
        return client
                .sendAsync(req, BodyHandlers.ofByteArray())
                .thenApply(this::translateResponse);
    }

}
