package global.maplink.geocode.extensions;

import global.maplink.extensions.SdkExtension;
import global.maplink.geocode.schema.GeocodeServiceRequest;
import global.maplink.geocode.schema.v1.suggestions.SuggestionsResult;
import lombok.RequiredArgsConstructor;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.function.Function;
import java.util.stream.Collectors;

import static java.util.Optional.ofNullable;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toMap;

public class GeocodeExtensionManager {
    private final Map<Class<GeocodeServiceRequest>, GeocodeExtension<GeocodeServiceRequest>> group;

    public GeocodeExtensionManager(Collection<GeocodeExtension<GeocodeServiceRequest>> extensions) {
        group = Collections.unmodifiableMap(
                extensions.stream()
                        .collect(groupingBy(GeocodeExtension::getRequestType))
                        .entrySet()
                        .stream()
                        .map(e -> new AbstractMap.SimpleEntry<>(e.getKey(), parse(e.getValue())))
                        .collect(toMap(Map.Entry::getKey, Map.Entry::getValue))
        );
    }

    @SuppressWarnings("unchecked")
    public <T extends GeocodeServiceRequest> GeocodeExtension<T> get(Class<T> requestType) {
        return ofNullable(group.get(requestType))
                .map(v -> (GeocodeExtension<T>) v)
                .orElseGet(() -> GeocodeExtension.empty(requestType));
    }

    private <T extends GeocodeServiceRequest> GeocodeExtension<T> parse(List<GeocodeExtension<T>> list) {
        Set<GeocodeExtension<T>> sorted = new TreeSet<>(list);
        GeocodeExtension<T> lastResult = null;
        for (GeocodeExtension<T> current : sorted) {
            if (lastResult == null) {
                lastResult = current;
                continue;
            }
            lastResult = new RecursiveExtensionIterator<>(current, lastResult);
        }
        return lastResult;
    }

    @SuppressWarnings("unchecked")
    public static GeocodeExtensionManager from(Collection<SdkExtension> extensions) {
        return new GeocodeExtensionManager(extensions.stream()
                .filter(e -> e instanceof GeocodeExtension)
                .map(e -> (GeocodeExtension<GeocodeServiceRequest>) e)
                .collect(Collectors.toSet()));
    }

    @RequiredArgsConstructor
    private static class RecursiveExtensionIterator<T extends GeocodeServiceRequest> implements GeocodeExtension<T> {

        private final GeocodeExtension<T> extension;
        private final GeocodeExtension<T> next;

        @Override
        public CompletableFuture<SuggestionsResult> doRequest(T request, Function<T, CompletableFuture<SuggestionsResult>> action) {
            return extension.doRequest(request, (req) -> next.doRequest(req, action));
        }

        @Override
        public CompletableFuture<SuggestionsResult> processResponse(SuggestionsResult response) {
            return extension.processResponse(response);
        }

        @Override
        public CompletableFuture<T> processRequest(T response) {
            return extension.processRequest(response);
        }

        @Override
        public String getName() {
            return extension.getName();
        }

        @Override
        public Class<T> getRequestType() {
            return extension.getRequestType();
        }

        @Override
        public int getPriority() {
            return extension.getPriority();
        }

        @Override
        public int compareTo(SdkExtension o) {
            return extension.compareTo(o);
        }
    }

}
