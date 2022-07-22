package global.maplink.http;

import global.maplink.http.request.Request;

import java.util.concurrent.CompletableFuture;

import static java.util.concurrent.CompletableFuture.completedFuture;

public class MockHttpAsyncEngine implements HttpAsyncEngine {
    @Override
    public CompletableFuture<Response> run(Request request) {
        return completedFuture(null);
    }
}
