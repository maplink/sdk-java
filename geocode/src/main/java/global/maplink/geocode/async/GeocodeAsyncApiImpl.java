package global.maplink.geocode.async;

import global.maplink.credentials.MapLinkCredentials;
import global.maplink.env.Environment;
import global.maplink.geocode.extensions.GeocodeExtensionManager;
import global.maplink.geocode.schema.GeocodeServiceRequest;
import global.maplink.geocode.schema.GeocodeSplittableRequest;
import global.maplink.geocode.schema.crossCities.CrossCitiesRequest;
import global.maplink.geocode.schema.reverse.ReverseRequest;
import global.maplink.geocode.schema.structured.StructuredRequest;
import global.maplink.geocode.schema.suggestions.SuggestionsRequest;
import global.maplink.geocode.schema.suggestions.SuggestionsResult;
import global.maplink.helpers.FutureHelper;
import global.maplink.http.HttpAsyncEngine;
import global.maplink.json.JsonMapper;
import global.maplink.token.TokenProvider;
import lombok.RequiredArgsConstructor;
import lombok.val;

import java.util.concurrent.CompletableFuture;

import static global.maplink.geocode.schema.suggestions.SuggestionsResult.EMPTY;
import static java.util.stream.Collectors.toList;
import static lombok.AccessLevel.PACKAGE;

@RequiredArgsConstructor(access = PACKAGE)
public class GeocodeAsyncApiImpl implements GeocodeAsyncAPI {

    private final Environment environment;

    private final HttpAsyncEngine http;

    private final JsonMapper mapper;

    private final TokenProvider tokenProvider;

    private final MapLinkCredentials credentials;

    private final GeocodeExtensionManager extensionManager;

    @Override
    public CompletableFuture<SuggestionsResult> suggestions(SuggestionsRequest request) {
        return extensionManager.get(SuggestionsRequest.class).doRequest(request, this::run);
    }

    @Override
    public CompletableFuture<SuggestionsResult> structured(StructuredRequest request) {
        return extensionManager.get(StructuredRequest.class).doRequest(request, this::run);
    }

    @Override
    public CompletableFuture<SuggestionsResult> reverse(ReverseRequest request) {
        return extensionManager.get(ReverseRequest.class).doRequest(request, this::runSplit);
    }

    @Override
    public CompletableFuture<SuggestionsResult> crossCities(CrossCitiesRequest request) {
        return extensionManager.get(CrossCitiesRequest.class).doRequest(request, this::run);
    }

    private CompletableFuture<SuggestionsResult> run(GeocodeServiceRequest request) {
        val httpRequest = request.asHttpRequest(environment, mapper);
        return credentials.fetchToken(tokenProvider)
                .thenCompose(token -> http.run(token.applyOn(httpRequest)))
                .thenApply(request.getResponseParser(mapper));
    }

    private CompletableFuture<SuggestionsResult> runSplit(GeocodeSplittableRequest request) {
        val requests = request.split().stream().map(this::run).collect(toList());
        return CompletableFuture.allOf(requests.toArray(new CompletableFuture[0]))
                .thenApply(v -> requests.stream()
                        .map(FutureHelper::await)
                        .reduce(SuggestionsResult::joinTo)
                        .orElse(EMPTY)
                );

    }

}
