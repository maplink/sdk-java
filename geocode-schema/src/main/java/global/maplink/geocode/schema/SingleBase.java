package global.maplink.geocode.schema;

import global.maplink.geocode.schema.v2.Type;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Getter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
public class SingleBase  {

    private String id;
    private String road;
    private Integer number;
    private String zipcode;
    private String district;
    private String city;
    private String state;
    private String acronym;
    private Type type;

}

