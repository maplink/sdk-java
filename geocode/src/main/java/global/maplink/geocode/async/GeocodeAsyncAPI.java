package global.maplink.geocode.async;

import global.maplink.MapLinkSDK;
import global.maplink.env.Environment;
import global.maplink.geocode.suggestions.SuggestionsRequest;
import global.maplink.geocode.suggestions.SuggestionsResponse;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;

public interface GeocodeAsyncAPI {

    default CompletableFuture<SuggestionsResponse> suggestions(String query) {
        return suggestions(SuggestionsRequest.builder().query(query).build());
    }

    CompletableFuture<SuggestionsResponse> suggestions(SuggestionsRequest request);


    static GeocodeAsyncAPI getInstance() {
        return getInstance(null);
    }

    static GeocodeAsyncAPI getInstance(Environment environment) {
        MapLinkSDK sdk = MapLinkSDK.getInstance();
        return new GeocodeAsyncApiImpl(
                Optional.ofNullable(environment).orElse(sdk.getEnvironment()),
                sdk.getHttp(),
                sdk.getJsonMapper(),
                sdk.getTokenProvider(),
                sdk.getCredentials()
        );
    }

}
