package global.maplink.geocode.extensions;

import global.maplink.extensions.SdkExtension;
import global.maplink.geocode.schema.GeocodeServiceRequest;
import global.maplink.geocode.schema.suggestions.SuggestionsResult;

import java.util.concurrent.CompletableFuture;
import java.util.function.Function;

import static java.lang.String.format;
import static java.util.concurrent.CompletableFuture.completedFuture;

public interface GeocodeExtension<REQ extends GeocodeServiceRequest> extends SdkExtension {

    Class<REQ> getRequestType();

    default CompletableFuture<SuggestionsResult> doRequest(REQ request, Function<REQ, CompletableFuture<SuggestionsResult>> action) {
        return processRequest(request).thenCompose(action).thenCompose(this::processResponse);
    }

    default CompletableFuture<REQ> processRequest(REQ response) {
        return completedFuture(response);
    }

    default CompletableFuture<SuggestionsResult> processResponse(SuggestionsResult response) {
        return completedFuture(response);
    }


    static <T extends GeocodeServiceRequest> GeocodeExtension<T> empty(Class<T> requestType) {
        return new GeocodeExtension<T>() {
            private final String name = format("Empty extension for [%s]", requestType.getName());

            @Override
            public Class<T> getRequestType() {
                return requestType;
            }

            @Override
            public String getName() {
                return name;
            }
        };
    }
}
