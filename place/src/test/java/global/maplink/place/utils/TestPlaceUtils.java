package global.maplink.place.utils;

import global.maplink.domain.MaplinkPoint;
import global.maplink.place.schema.Address;
import global.maplink.place.schema.Place;
import global.maplink.place.schema.SubCategory;

import java.util.HashSet;
import java.util.Set;

import static global.maplink.place.schema.Category.POSTOS_DE_COMBUSTIVEL;

public class TestPlaceUtils {
    public static final String[] LIST_ALL_STATES_EXPECTED_RESULT = new String[]{"SP", "RJ"};
    public static final String LIST_ALL_DISTRICTS_EXPECTED_RESULT = "José Menino";

    public static Place testPlaceCreator(String name, String id, String state, String city, String district) {
        MaplinkPoint point = new MaplinkPoint(-23.368653161261896,-46.77969932556152);

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

        return Place.builder()
                .id(id)
                .name(name)
                .documentNumber("444455")
                .category(POSTOS_DE_COMBUSTIVEL)
                .subCategory(SubCategory.POSTOS_DE_COMBUSTIVEL)
                .address(address)
                .phones(phones)
                .tag("abc123")
                .tag("good_place_to_live")
                .active(true)
                .build();
    }
}
