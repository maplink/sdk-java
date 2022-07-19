package global.maplink.geocode.suggestions;

import lombok.AllArgsConstructor;

import java.util.List;

import static java.util.Collections.emptyList;

@AllArgsConstructor
public class SuggestionsResponse {

    private final int found = 0;

    private final List<String> results = emptyList();
}
