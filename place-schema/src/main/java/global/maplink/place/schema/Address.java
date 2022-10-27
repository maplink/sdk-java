package global.maplink.place.schema;

import global.maplink.place.schema.exception.PlaceUpdateException;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.util.Objects;

import static lombok.AccessLevel.PRIVATE;

@Data
@Builder
@RequiredArgsConstructor
@NoArgsConstructor(force = true, access = PRIVATE)
public class Address {
    private final String street;
    private final String number;
    private final String district;
    private final String city;
    private final String state;
    private final String zipcode;
    private final String complement;
    private final Point point;

    public void validate() {
        if (Place.isInvalid(street)) {
            throw PlaceUpdateException.of("address.street");
        }

        if (Place.isInvalid(city)) {
            throw PlaceUpdateException.of("address.city");
        }

        if (Place.isInvalid(state)) {
            throw PlaceUpdateException.of("address.state");
        }

        if (Place.isInvalid(number)) {
            throw PlaceUpdateException.of("address.number");
        }

        if (Place.isInvalid(zipcode)) {
            throw PlaceUpdateException.of("address.zipcode");
        }

        if (Objects.isNull(point)) {
            throw PlaceUpdateException.of("address.point");
        }
    }
}
