package global.maplink.trip.schema.solution;

import gloabl.maplink.toll.schema.TollCondition;
import gloabl.maplink.toll.schema.TollServiceType;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class CalculationDetail {
    private final String id;
    private final String name;
    private final String address;
    private final String city;
    private final String state;
    private final String country;
    private final String concession;
    private final String direction;
    private final Coordinates coordinates;
    private final List<TollServiceType> serviceTypes;
    private final BigDecimal price;
    private final List<TollCondition> conditions;
}
