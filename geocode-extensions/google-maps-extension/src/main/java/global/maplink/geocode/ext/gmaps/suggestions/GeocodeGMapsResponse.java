package global.maplink.geocode.ext.gmaps.suggestions;

import global.maplink.geocode.schema.v1.suggestions.SuggestionsResult;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static java.util.stream.Collectors.toList;

@RequiredArgsConstructor
@NoArgsConstructor(force = true)
@Data
public class GeocodeGMapsResponse {
    public static final String STATUS_DENIED = "REQUEST_DENIED";
    public static final String STATUS_EMPTY = "ZERO_RESULTS";
    public static final String STATUS_OK = "OK";
    private final List<GeocodeGMapsResult> results;
    private final String status;
    private final String error_message;

    public boolean isDenied() {
        return STATUS_DENIED.equalsIgnoreCase(status);
    }

    public boolean isEmpty() {
        return STATUS_EMPTY.equalsIgnoreCase(status);
    }

    public boolean isOk() {
        return STATUS_OK.equalsIgnoreCase(status);
    }

    public SuggestionsResult toSuggestions() {
        if (results == null || results.isEmpty()) {
            return SuggestionsResult.EMPTY;
        }
        return SuggestionsResult.builder()
                .found(results.size())
                .results(results.stream()
                        .map(GeocodeGMapsResult::toSuggestion)
                        .collect(toList())
                ).build();
    }
}
