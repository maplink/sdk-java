package global.maplink.geocode.async.v2;

import global.maplink.MapLinkSDK;
import global.maplink.env.Environment;
import global.maplink.geocode.async.GeocodeAsyncAPIBase;
import global.maplink.geocode.extensions.GeocodeExtensionManager;
import global.maplink.geocode.schema.v2.suggestions.SuggestionsResult;
import global.maplink.geocode.schema.v2.Type;
import global.maplink.geocode.schema.v2.reverse.ReverseBaseRequest;
import global.maplink.geocode.schema.v2.suggestions.SuggestionsBaseRequest;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

import static global.maplink.MapLinkServiceRequestAsyncRunner.createRunner;
import static java.util.Arrays.asList;

public interface GeocodeAsyncAPI extends GeocodeAsyncAPIBase {

    default CompletableFuture<SuggestionsResult> suggestions(String query) {
        return suggestions(SuggestionsBaseRequest.builder().query(query).build());
    }

    default CompletableFuture<SuggestionsResult> suggestions(String query, Type type) {
        return suggestions(SuggestionsBaseRequest.builder().query(query).type(type).build());
    }

    default CompletableFuture<SuggestionsResult> reverse(ReverseBaseRequest.Entry... request) {
        return reverse(asList(request));
    }

    default CompletableFuture<SuggestionsResult> reverse(List<ReverseBaseRequest.Entry> request) {
        return reverse(ReverseBaseRequest.of(request));
    }

    static GeocodeAsyncAPI getInstance() {
        return getInstance(null);
    }

    static GeocodeAsyncAPI getInstance(Environment environment) {
        MapLinkSDK sdk = MapLinkSDK.getInstance();
        return new GeocodeAsyncApiImpl(
                createRunner(
                        Optional.ofNullable(environment).orElse(sdk.getEnvironment()),
                        sdk
                ),
                GeocodeExtensionManager.from(sdk.getExtensions())
        );
    }

}
