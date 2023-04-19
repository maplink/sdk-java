package global.maplink.place.utils;

import global.maplink.place.schema.Address;
import global.maplink.place.schema.Place;
import global.maplink.place.schema.Point;
import global.maplink.place.schema.SubCategory;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

import static global.maplink.place.schema.Category.POSTOS_DE_COMBUSTIVEL;

public class TestPlaceUtils {
    public static final String[] LIST_ALL_STATES_EXPECTED_RESULT = new String[]{"SP", "RJ"};
    public static final String LIST_ALL_DISTRICTS_EXPECTED_RESULT = "[José Menino]";

    public static Place testPlaceCreator(String name, String id, String state, String city, String district) {
        Point point = Point.builder()
                .latitude(BigDecimal.valueOf(-23.368653161261896))
                .longitude(BigDecimal.valueOf(-46.77969932556152))
                .build();

        Address address = Address.builder()
                .state(state)
                .city(city)
                .district(district)
                .street("Rua das Flores")
                .number("23")
                .zipcode("07700-000")
                .point(point)
                .build();

        Set<String> phones = new HashSet<>();
        phones.add("(11) 5080-5518");

        Set<String> tags = new HashSet<>();
        tags.add("abc123");
        tags.add("good_place_to_live");

        return Place.builder()
                .id(id)
                .name(name)
                .documentNumber("444455")
                .category(POSTOS_DE_COMBUSTIVEL)
                .subCategory(SubCategory.POSTOS_DE_COMBUSTIVEL)
                .address(address)
                .phones(phones)
                .tags(tags)
                .active(true)
                .build();
    }
}
