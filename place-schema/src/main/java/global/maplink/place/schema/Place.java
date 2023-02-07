package global.maplink.place.schema;

import global.maplink.place.schema.exception.PlaceUpdateException;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.util.Map;
import java.util.Set;

import static java.util.Objects.isNull;
import static lombok.AccessLevel.PRIVATE;

@Data
@Builder
@RequiredArgsConstructor(staticName = "of")
@NoArgsConstructor(force = true, access = PRIVATE)
public class Place {
    private final String id;
    private final String name;
    private final String documentNumber;
    private final String description;
    private final String url;
    private final String website;
    private final String favicon;
    private final OpeningHours openingHours;
    private final Set<PaymentMethod> payments;
    private final Category category;
    private final SubCategory subCategory;
    private final Address address;
    private final Set<String> phones;
    private final Map<String, String> additionalInfo;
    private final Set<SocialConnection> connections;
    private final Set<String> tags;
    private final boolean active;
    private final String clientId;

    public void validate() {
        if (isInvalid(id)) {
            throw PlaceUpdateException.of("id");
        }

        if (isInvalid(name)) {
            throw PlaceUpdateException.of("name");
        }

        if (isNull(category)) {
            throw PlaceUpdateException.of("category");
        }

        if (isNull(subCategory)) {
            throw PlaceUpdateException.of("subCategory");
        }

        if (isNull(address)) {
            throw PlaceUpdateException.of("subCategory");
        }

        address.validate();
    }

    private boolean isInvalid(final String value) {
        return isNull(value) || value.trim().isEmpty();
    }
}
