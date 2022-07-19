package global.maplink.geocode.async;

import global.maplink.env.Environment;
import global.maplink.geocode.suggestions.SuggestionsRequest;
import global.maplink.geocode.suggestions.SuggestionsResponse;
import global.maplink.http.HttpAsyncEngine;
import global.maplink.http.request.Request;
import global.maplink.json.JsonMapper;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

import java.util.concurrent.CompletableFuture;

@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
public class GeocodeAsyncApiImpl implements GeocodeAsyncAPI {

    private static final String SUGGESTIONS_PATH = "geocode/v1/suggestions";

    private final Environment environment;

    private final HttpAsyncEngine http;

    private final JsonMapper mapper;

    @Override
    public CompletableFuture<SuggestionsResponse> suggestions(SuggestionsRequest request) {
        return http.run(
                Request.get(environment.withService(SUGGESTIONS_PATH))
                        .withQuery("q", request.getQuery())
                        .withQuery("type", request.getType().name())
        ).thenApply(v -> v.parseBodyObject(mapper, SuggestionsResponse.class));
    }

}
