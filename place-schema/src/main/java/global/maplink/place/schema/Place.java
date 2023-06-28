package global.maplink.place.schema;

import global.maplink.place.schema.exception.PlaceUpdateViolation;
import global.maplink.validations.Validable;
import global.maplink.validations.ValidationViolation;
import lombok.*;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static java.util.Objects.isNull;
import static lombok.AccessLevel.PRIVATE;

@Data
@Builder
@RequiredArgsConstructor(staticName = "of")
@NoArgsConstructor(force = true, access = PRIVATE)
public class Place implements Validable {
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
    @Singular
    private final Set<String> tags;
    private final boolean active;
    private final String clientId;

    @Override
    public List<ValidationViolation> validate() {
        List<ValidationViolation> violations = new LinkedList<>();
        if (isInvalid(id)) {
            violations.add(PlaceUpdateViolation.of("id"));
        }

        if (isInvalid(name)) {
            violations.add(PlaceUpdateViolation.of("name"));
        }

        if (isNull(category)) {
            violations.add(PlaceUpdateViolation.of("category"));
        }

        if (isNull(subCategory)) {
            violations.add(PlaceUpdateViolation.of("subCategory"));
        }

        if (isNull(address)) {
            violations.add(PlaceUpdateViolation.of("address"));
        } else {
            violations.addAll(address.validate());
        }

        return violations;
    }

    private boolean isInvalid(final String value) {
        return isNull(value) || value.trim().isEmpty();
    }
}
