package global.maplink;

import global.maplink.credentials.MapLinkCredentials;
import global.maplink.env.Environment;
import global.maplink.extensions.SdkExtension;
import global.maplink.extensions.SdkRequestFilterExtension;
import global.maplink.http.HttpAsyncEngine;
import global.maplink.http.request.Request;
import global.maplink.json.JsonMapper;
import global.maplink.token.TokenProvider;
import lombok.Getter;
import lombok.val;

import java.util.Collection;
import java.util.concurrent.CompletableFuture;
import java.util.function.Function;

import static java.util.stream.Collectors.toList;

public class MapLinkServiceRequestAsyncRunnerImpl implements MapLinkServiceRequestAsyncRunner {

    @Getter
    private final Environment environment;

    private final HttpAsyncEngine http;

    private final JsonMapper mapper;

    private final TokenProvider tokenProvider;

    private final MapLinkCredentials credentials;

    private final Collection<SdkRequestFilterExtension> requestFilters;

    public MapLinkServiceRequestAsyncRunnerImpl(
            Environment environment,
            HttpAsyncEngine http,
            JsonMapper mapper,
            TokenProvider tokenProvider,
            MapLinkCredentials credentials,
            Collection<SdkExtension> extensions
    ) {
        this.environment = environment;
        this.http = http;
        this.mapper = mapper;
        this.tokenProvider = tokenProvider;
        this.credentials = credentials;
        this.requestFilters = extensions.stream()
                .filter(it -> it instanceof SdkRequestFilterExtension)
                .map(it -> (SdkRequestFilterExtension) it)
                .sorted()
                .collect(toList());
    }


    @Override
    public <T> CompletableFuture<T> run(MapLinkServiceRequest<T> request) {
        request.throwIfInvalid();
        val httpRequest = request.asHttpRequest(environment, mapper);
        return credentials.fetchToken(tokenProvider)
                .thenApply(token -> token.applyOn(httpRequest))
                .thenApply(this.applyFilters(request))
                .thenCompose(http::run)
                .thenApply(request.getResponseParser(mapper));
    }

    private <T> Function<Request, Request> applyFilters(MapLinkServiceRequest<T> serviceRequest) {
        return (request) -> {
            Request updatedRequest = request;
            for (SdkRequestFilterExtension filter : requestFilters) {
                updatedRequest = filter.filter(serviceRequest, updatedRequest);
            }
            return updatedRequest;
        };
    }

}
