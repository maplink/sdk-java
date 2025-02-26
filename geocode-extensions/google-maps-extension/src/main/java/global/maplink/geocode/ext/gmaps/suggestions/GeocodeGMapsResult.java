package global.maplink.geocode.ext.gmaps.suggestions;

import global.maplink.geocode.schema.GeoPoint;
import global.maplink.geocode.schema.State;
import global.maplink.geocode.schema.Type;
import global.maplink.geocode.schema.v1.Address;
import global.maplink.geocode.schema.v1.suggestions.Suggestion;
import global.maplink.geocode.schema.v2.AddressBase;
import global.maplink.geocode.schema.v2.TypeVersionTwo;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.util.*;
import java.util.function.Consumer;
import java.util.function.Function;

import static java.util.Objects.nonNull;
import static java.util.stream.Collectors.toMap;

@RequiredArgsConstructor
@NoArgsConstructor(force = true)
@Data
public class GeocodeGMapsResult {

    private final List<GMapsAddressComponent> address_components;
    private final String formatted_address;
    private final GMapsGeometry geometry;
    private final String place_id;
    private final Set<String> types;
    private final boolean partial_match;

    public Suggestion toSuggestion() {
        AddressBase addr = toAddress();
        return Suggestion.builder()
                .type(typeOf(addr))
                .address(addr)
                .id(place_id)
                .label(formatted_address)
                .build();
    }

    private Type typeOf(AddressBase addr) {
        if (nonNull(addr.getRoad())) return TypeVersionTwo.ROAD;
        if (nonNull(addr.getZipCode())) return TypeVersionTwo.ZIPCODE;
        if (nonNull(addr.getDistrict())) return TypeVersionTwo.DISTRICT;
        if (nonNull(addr.getCity())) return TypeVersionTwo.CITY;
        if (nonNull(addr.getState())) return TypeVersionTwo.STATE;
        return null;
    }

    private AddressBase toAddress() {
        Address.AddressBuilder<?, ?> builder = Address.builder();
        if (address_components != null && !address_components.isEmpty()) {
            Components components = new Components();
            components.fill(Components.COUNTRY, builder::country);
            components.fill(Components.STATE, c -> new State(c.getShort_name(), c.getLong_name()), builder::state);
            components.fill(Components.CITY, builder::city);
            components.fill(Components.DISTRICT, builder::district);
            components.fill(Components.ZIPCODE, builder::zipCode);
            components.fill(Components.ROAD, builder::road);
            components.fill(Components.STREET_NUMBER, builder::number);
        }
        Optional.ofNullable(geometry)
                .map(GMapsGeometry::getLocation)
                .map(l -> GeoPoint.of(l.getLat(), l.getLng()))
                .ifPresent(builder::mainLocation);

        return builder.build();
    }

    private class Components {
        static final String STREET_NUMBER = "street_number";
        static final String ROAD = "route";
        static final String ZIPCODE = "postal_code";
        static final String DISTRICT = "sublocality_level_1";
        static final String CITY = "administrative_area_level_2";
        static final String STATE = "administrative_area_level_1";
        static final String COUNTRY = "country";

        private final Map<String, GMapsAddressComponent> indexed = address_components.stream()
                .flatMap(v -> v.getTypes().stream().map(t -> new AbstractMap.SimpleEntry<>(t, v)))
                .collect(toMap(Map.Entry::getKey, Map.Entry::getValue, (v1, v2) -> v1));

        private void fill(String component, Consumer<String> setter) {
            fill(component, GMapsAddressComponent::getLong_name, setter);
        }

        private <T> void fill(String component, Function<GMapsAddressComponent, T> transform, Consumer<T> setter) {
            if (indexed.containsKey(component)) {
                setter.accept(transform.apply(indexed.get(component)));
            }
        }
    }

}
