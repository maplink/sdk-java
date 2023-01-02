package global.maplink.http;

import global.maplink.http.request.*;
import lombok.val;

import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.concurrent.CompletableFuture;
import java.util.function.BiFunction;
import java.util.function.Function;

public class HttpAsyncEngineJava11Impl implements HttpAsyncEngine {

    public static final String HEADER_CONTENT_TYPE = "content-type";

    private final HttpClient client = HttpClient.newHttpClient();

    @Override
    public CompletableFuture<Response> run(Request request) {
        if (request instanceof GetRequest) return runWithoutBody(request, HttpRequest.Builder::GET);
        if (request instanceof PostRequest) return runWithBody((PostRequest) request, HttpRequest.Builder::POST);
        if (request instanceof PutRequest) return runWithBody((PutRequest) request, HttpRequest.Builder::PUT);

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

    private CompletableFuture<Response> runWithoutBody(
            Request request,
            Function<HttpRequest.Builder, HttpRequest.Builder> httpVerb
    ) {
        val req = httpVerb.apply(buildBaseRequest(request))
                .build();
        return client
                .sendAsync(req, BodyHandlers.ofByteArray())
                .thenApply(this::translateResponse);
    }

    private CompletableFuture<Response> runWithBody(
            WithBodyRequest request,
            BiFunction<HttpRequest.Builder, HttpRequest.BodyPublisher, HttpRequest.Builder> httpVerb
    ) {
        val req = httpVerb.apply(buildBaseRequest(request), BodyPublishers.ofByteArray(request.getBody()))
                .build();
        return client
                .sendAsync(req, BodyHandlers.ofByteArray())
                .thenApply(this::translateResponse);
    }
}
