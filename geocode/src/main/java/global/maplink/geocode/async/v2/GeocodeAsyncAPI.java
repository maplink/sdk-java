package global.maplink.geocode.async.v2;

import global.maplink.MapLinkSDK;
import global.maplink.env.Environment;
import global.maplink.geocode.GeocodeVersion;
import global.maplink.geocode.extensions.GeocodeExtensionManager;
import global.maplink.geocode.runner.GeocodeEnvironmentDecorator;
import global.maplink.geocode.schema.reverse.ReverseRequest;
import global.maplink.geocode.schema.structured.StructuredRequest;
import global.maplink.geocode.schema.suggestions.SuggestionsRequest;
import global.maplink.geocode.schema.suggestions.SuggestionsResult;
import global.maplink.geocode.schema.Type;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

import static global.maplink.MapLinkServiceRequestAsyncRunner.createRunner;
import static java.util.Arrays.asList;

public interface GeocodeAsyncAPI  {


    default CompletableFuture<SuggestionsResult> suggestions(String query) {
        return suggestions(SuggestionsRequest.builder().query(query).build());
    }

    default CompletableFuture<SuggestionsResult> suggestions(String query, Type type) {
        return suggestions(SuggestionsRequest.builder().query(query).type(type).build());
    }

    CompletableFuture<SuggestionsResult> suggestions(SuggestionsRequest request);

    default CompletableFuture<SuggestionsResult> reverse(ReverseRequest.Entry... request) {
        return reverse(asList(request));
    }

    default CompletableFuture<SuggestionsResult> reverse(List<ReverseRequest.Entry> request) {
        return reverse(ReverseRequest.of(request));
    }

    CompletableFuture<SuggestionsResult> structured(StructuredRequest request);

    CompletableFuture<SuggestionsResult> reverse(ReverseRequest request);

    static GeocodeAsyncAPI getInstance() {
        return getInstance(null, GeocodeVersion.V2);
    }

    static GeocodeAsyncAPI getInstance(Environment environment) {
        return getInstance(environment, GeocodeVersion.V2);
    }

    static GeocodeAsyncAPI getInstance(GeocodeVersion version) {
        return getInstance(null, version);
    }

    static GeocodeAsyncAPI getInstance(Environment environment, GeocodeVersion version) {
        MapLinkSDK sdk = MapLinkSDK.getInstance();
        Environment env = Optional.ofNullable(environment).orElse(sdk.getEnvironment());
        return new GeocodeAsyncApiImpl(
                createRunner(
                        new GeocodeEnvironmentDecorator(env, version),
                        sdk
                ),
                GeocodeExtensionManager.from(sdk.getExtensions())
        );
    }

}
