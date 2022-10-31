package global.maplink.place.schema;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.util.Map;
import java.util.Set;

import static lombok.AccessLevel.PRIVATE;

@Data
@Builder
@RequiredArgsConstructor(staticName = "of")
@NoArgsConstructor(force = true, access = PRIVATE)
public class PlaceRoute {
    private final String id;
    private final String name;
    private final String documentNumber;
    private final Category category;
    private final SubCategory subCategory;
    private final Address address;
    private final String website;
    private final Set<String> phones;
    private final String clientId;
    private final Map<String, String> additionalInfo;
    private final boolean active;
}
